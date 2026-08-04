package com.betacom.fe.services.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSession;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerSessionCreateParams;
import com.stripe.param.PaymentIntentCreateParams;

import com.betacom.fe.dto.input.PaymentIntentReq;
import com.betacom.fe.dto.input.RicevutaReq;
import com.betacom.fe.dto.output.PaymentIntentDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.models.Ordini;
import com.betacom.fe.models.Pagamenti;
import com.betacom.fe.models.StatoOrdine;
import com.betacom.fe.models.StatoPagamento;
import com.betacom.fe.models.User;
import com.betacom.fe.repositories.ICarrelloRepository;
import com.betacom.fe.repositories.IOrdineRepository;
import com.betacom.fe.repositories.IPagamentiRepository;
import com.betacom.fe.repositories.IProdottiCarrelloRepository;
import com.betacom.fe.repositories.IStatoOrdineRepository;
import com.betacom.fe.repositories.IStatoPagamentoRepository;
import com.betacom.fe.repositories.IUserRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IPagamentiServices;
import com.betacom.fe.services.interfaces.IRicevutaServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagamentiImpl implements IPagamentiServices {

    private final IPagamentiRepository pagRep;
    private final IOrdineRepository ordRep;
    private final IStatoPagamentoRepository statoRep;
    private final IMessaggioServices msgS;
    private final IRicevutaServices ricevutaS;
    private final IStatoOrdineRepository statoOrdRep;
    private final ICarrelloRepository carR;
    private final IProdottiCarrelloRepository proCarR;
    private final IUserRepository userRep;

    @Override
    @Transactional
    public PaymentIntentDTO createPaymentIntent(PaymentIntentReq req) throws Exception, StripeException {
        Ordini ordine = ordRep.findById(req.getIdOrdine())
                .orElseThrow(() -> new AcademyException(msgS.get("ordine.no.exists")));

        // riusa la riga esistente: tornando indietro a modificare l'ordine
        // non si creano righe doppie in pagamenti
        Pagamenti pagamento = pagRep.findByOrdineIdOrdine(req.getIdOrdine())
                .orElseGet(Pagamenti::new);

        if (pagamento.getStatoPagamento() != null
                && "Completato".equals(pagamento.getStatoPagamento().getStato())) {
            throw new AcademyException(msgS.get("pagamento.gia.completato"));
        }
        
        String customerId = getOrCreateStripeCustomer(ordine.getUserId());

        long amountInCents = Math.round(ordine.getTotale() * 100);

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("eur")
                .addPaymentMethodType("card")
                .addPaymentMethodType("klarna")
                .addPaymentMethodType("paypal")
                .setCustomer(customerId)
                .putMetadata("idOrdine", req.getIdOrdine().toString())
                .build();

        PaymentIntent intent = PaymentIntent.create(params);
        
        CustomerSession session = CustomerSession.create(
                CustomerSessionCreateParams.builder()
                        .setCustomer(customerId)
                        .setComponents(
                                CustomerSessionCreateParams.Components.builder()
                                        .setPaymentElement(
                                                CustomerSessionCreateParams.Components.PaymentElement.builder()
                                                        .setEnabled(true)
                                                        .setFeatures(
                                                                CustomerSessionCreateParams.Components.PaymentElement.Features.builder()
                                                                        .setPaymentMethodSave(
                                                                                CustomerSessionCreateParams.Components.PaymentElement.Features.PaymentMethodSave.ENABLED)
                                                                        .setPaymentMethodSaveUsage(
                                                                                CustomerSessionCreateParams.Components.PaymentElement.Features.PaymentMethodSaveUsage.ON_SESSION)
                                                                        .setPaymentMethodRedisplay(
                                                                                CustomerSessionCreateParams.Components.PaymentElement.Features.PaymentMethodRedisplay.ENABLED)
                                                                        .setPaymentMethodRemove(
                                                                                CustomerSessionCreateParams.Components.PaymentElement.Features.PaymentMethodRemove.ENABLED)
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());

        StatoPagamento inAttesa = statoRep.findByStato("In Attesa")
                .orElseThrow(() -> new AcademyException(msgS.get("stato.no.exists")));

        pagamento.setOrdine(ordine);
        pagamento.setImporto(ordine.getTotale());
        pagamento.setTransazioneId(intent.getId());
        pagamento.setStatoPagamento(inAttesa);
        pagamento.setDataPagamento(null);
        pagamento.setMetodoPagamento(null);
        pagRep.save(pagamento);

        log.info("PaymentIntent creato: {} per ordine {} (customer {})", intent.getId(), req.getIdOrdine(), customerId);

        return PaymentIntentDTO.builder()
                .clientSecret(intent.getClientSecret())
                .idPagamento(pagamento.getIdPagamento())
                .customerSessionClientSecret(session.getClientSecret())
                .build();
    }
    
    private String getOrCreateStripeCustomer(User user) throws StripeException {
        if (user.getStripeCustomerId() != null && !user.getStripeCustomerId().isBlank()) {
            return user.getStripeCustomerId();
        }
 
        Customer customer = Customer.create(
                CustomerCreateParams.builder()
                        .setEmail(user.getEmail())
                        .setName(user.getNome() + " " + user.getCognome())
                        .putMetadata("userId", user.getUserId().toString())
                        .build());
 
        user.setStripeCustomerId(customer.getId());
        userRep.save(user);
 
        log.info("Creato Customer Stripe {} per utente {}", customer.getId(), user.getUserId());
        return customer.getId();
    }

    @Override
    @Transactional
    public void markSucceeded(String transazioneId, String paymentMethodId) throws Exception {
        Pagamenti pagamento = pagRep.findByTransazioneId(transazioneId)
                .orElseThrow(() -> new AcademyException(msgS.get("pagamento.no.exists")));

        if (pagamento.getStatoPagamento() != null
                && "Completato".equals(pagamento.getStatoPagamento().getStato())) {
            log.info("Pagamento {} gia' completato, evento ignorato", transazioneId);
            return;
        }

        StatoPagamento completato = statoRep.findByStato("Completato")
                .orElseThrow(() -> new AcademyException(msgS.get("stato.no.exists")));

        pagamento.setDataPagamento(LocalDateTime.now());

        if (paymentMethodId != null) {
            PaymentMethod pm = PaymentMethod.retrieve(paymentMethodId);
            pagamento.setMetodoPagamento(pm.getType()); // "card", "klarna", "paypal"
        }

        pagamento.setStatoPagamento(completato);
        pagRep.save(pagamento);

        // l'ordine passa a Confermato
        Ordini ordine = pagamento.getOrdine();
        StatoOrdine confermato = statoOrdRep.findByStato("Confermato")
                .orElseThrow(() -> new AcademyException(msgS.get("stato.ordine.non.esiste")));
        ordine.setStato(confermato);
        ordRep.save(ordine);

        // svuota il carrello solo ora che il pagamento e' confermato
        carR.findByUserId_UserId(ordine.getUserId().getUserId())
            .ifPresent(c -> proCarR.deleteByCarrelloIdCarrello(c.getIdCarrello()));

        // emette la ricevuta (una per venditore)
        // fuori dal percorso critico: il pagamento e' gia' stato incassato da Stripe,
        // un errore qui non deve annullare lo stato del pagamento
        try {
            RicevutaReq rReq = new RicevutaReq();
            rReq.setOrdineId(ordine.getIdOrdine());
            ricevutaS.create(rReq);
        } catch (Exception e) {
            log.error("Ricevuta non emessa per ordine {}: {}",
                    ordine.getIdOrdine(), e.getMessage(), e);
        }

        log.info("Pagamento {} confermato, ordine {} confermato, ricevuta emessa",
                transazioneId, ordine.getIdOrdine());
    }

    @Override
    @Transactional
    public void markFailed(String transazioneId, String paymentMethodId) throws Exception {
        Pagamenti pagamento = pagRep.findByTransazioneId(transazioneId)
                .orElseThrow(() -> new AcademyException(msgS.get("pagamento.no.exists")));

        StatoPagamento fallito = statoRep.findByStato("Fallito")
                .orElseThrow(() -> new AcademyException(msgS.get("stato.no.exists")));

        pagamento.setDataPagamento(LocalDateTime.now());

        if (paymentMethodId != null) {
            PaymentMethod pm = PaymentMethod.retrieve(paymentMethodId);
            pagamento.setMetodoPagamento(pm.getType());
        }

        pagamento.setStatoPagamento(fallito);
        pagRep.save(pagamento);

        log.info("Pagamento {} fallito", transazioneId);
    }
}
