package com.betacom.fe.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;

import com.betacom.fe.dto.input.PaymentIntentReq;
import com.betacom.fe.dto.output.MetodoPagamentoDTO;
import com.betacom.fe.dto.output.PaymentIntentDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.models.MetodoPagamento;
import com.betacom.fe.models.Ordini;
import com.betacom.fe.models.Pagamenti;
import com.betacom.fe.models.StatoPagamento;
import com.betacom.fe.repositories.IMetodoPagamentoRepository;
import com.betacom.fe.repositories.IOrdineRepository;
import com.betacom.fe.repositories.IPagamentiRepository;
import com.betacom.fe.repositories.IStatoPagamentoRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IPagamentiServices;

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
    private final IMetodoPagamentoRepository metodoRep;
    private final IMessaggioServices msgS;

    @Override
    @Transactional
    public PaymentIntentDTO createPaymentIntent(PaymentIntentReq req) throws Exception, StripeException {
        Ordini ordine = ordRep.findById(req.getIdOrdine())
                .orElseThrow(() -> new AcademyException(msgS.get("ordine.no.exists")));

        long amountInCents = Math.round(ordine.getTotale() * 100);

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("eur")
                .addPaymentMethodType("card")
                .addPaymentMethodType("satispay")
                .addPaymentMethodType("scalapay")
                .putMetadata("idOrdine", req.getIdOrdine().toString())
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        StatoPagamento inAttesa = statoRep.findByStato("In attesa")
                .orElseThrow(() -> new AcademyException(msgS.get("stato.no.exists")));

        Pagamenti pagamento = new Pagamenti();
        pagamento.setOrdine(ordine);
        pagamento.setImporto(ordine.getTotale());
        pagamento.setTransazioneId(intent.getId());
        pagamento.setStatoPagamento(inAttesa);
        pagamento.setSalvato(Boolean.TRUE.equals(req.getSalvaMetodo()));
        pagRep.save(pagamento);

        log.info("PaymentIntent creato: {}", intent.getId());

        return PaymentIntentDTO.builder()
                .clientSecret(intent.getClientSecret())
                .idPagamento(pagamento.getIdPagamento())
                .build();
    }

    @Override
    @Transactional
    public void markSucceeded(String transazioneId, String paymentMethodId) throws Exception {
        Pagamenti pagamento = pagRep.findByTransazioneId(transazioneId)
                .orElseThrow(() -> new AcademyException(msgS.get("pagamento.no.exists")));

        StatoPagamento completato = statoRep.findByStato("Completato")
                .orElseThrow(() -> new AcademyException(msgS.get("stato.no.exists")));

        if (paymentMethodId != null) {
            PaymentMethod pm = PaymentMethod.retrieve(paymentMethodId);

            // ALWAYS record what was used
            pagamento.setMetodoPagamento(pm.getType()); // "card", "satispay", ...

            // Only if the user asked to save it: create the wallet row and link it
            if (Boolean.TRUE.equals(pagamento.getSalvato())) {
                MetodoPagamento nuovoMP = new MetodoPagamento();
                nuovoMP.setTipo(pm.getType());
                nuovoMP.setDettagli(buildDettagli(pm));
                nuovoMP.setIsPredefinito(false);
                nuovoMP.setUserId(pagamento.getOrdine().getUserId()); // user comes from the order
                metodoRep.save(nuovoMP);

                pagamento.setMetodoSalvato(nuovoMP);
            }
        }

        pagamento.setStatoPagamento(completato);
        pagamento.setDataPagamento(LocalDateTime.now());
        pagRep.save(pagamento);
        log.info("Pagamento {} confermato, metodo: {}", transazioneId, pagamento.getMetodoPagamento());
    }

    // Human-readable description of the method
    private String buildDettagli(PaymentMethod pm) {
        if ("card".equals(pm.getType()) && pm.getCard() != null) {
            return pm.getCard().getBrand() + " **** " + pm.getCard().getLast4(); // "visa **** 4242"
        }
        return pm.getType();
    }
    
    @Override
    @Transactional
    public void markFailed(String transazioneId) throws Exception {
        Pagamenti pagamento = pagRep.findByTransazioneId(transazioneId)
                .orElseThrow(() -> new AcademyException(msgS.get("pagamento.no.exists")));

        StatoPagamento fallito = statoRep.findByStato("Fallito")
                .orElseThrow(() -> new AcademyException(msgS.get("stato.no.exists")));

        pagamento.setStatoPagamento(fallito);
        pagRep.save(pagamento);
        log.info("Pagamento {} fallito", transazioneId);
    }

	@Override
	public List<MetodoPagamentoDTO> getMetodiSalvatiByOrdine(Integer idOrdine) throws Exception {
		Ordini ordine = ordRep.findById(idOrdine)
	            .orElseThrow(() -> new AcademyException(msgS.get("ordine.no.exists")));

	    return metodoRep.findByUserId(ordine.getUserId()).stream()
	            .map(m -> MetodoPagamentoDTO.builder()
	                    .idMetodo(m.getIdMetodo())
	                    .tipo(m.getTipo())
	                    .dettagli(m.getDettagli())
	                    .build())
	            .toList();
	}
}