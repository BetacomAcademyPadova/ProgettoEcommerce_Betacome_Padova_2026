package com.betacom.fe.services.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import com.betacom.fe.dto.input.PaymentIntentReq;
import com.betacom.fe.dto.output.PaymentIntentDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.models.Ordini;
import com.betacom.fe.models.Pagamenti;
import com.betacom.fe.models.StatoPagamento;
import com.betacom.fe.repositories.IOrdiniRepository;
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
    private final IOrdiniRepository ordRep;
    private final IStatoPagamentoRepository statoRep;
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

        StatoPagamento inAttesa = statoRep.findById("In attesa")
                .orElseThrow(() -> new AcademyException(msgS.get("stato.no.exists")));

        Pagamenti pagamento = new Pagamenti();
        pagamento.setOrdine(ordine);
        pagamento.setImporto(ordine.getTotale());
        pagamento.setTransazioneId(intent.getId());
        pagamento.setStatoPagamento(inAttesa);
        pagRep.save(pagamento);

        log.info("PaymentIntent creato: {}", intent.getId());

        return PaymentIntentDTO.builder()
                .clientSecret(intent.getClientSecret())
                .idPagamento(pagamento.getIdPagamento())
                .build();
    }

    @Override
    @Transactional
    public void markSucceeded(String transazioneId) throws Exception {
        Pagamenti pagamento = pagRep.findByTransazioneId(transazioneId)
                .orElseThrow(() -> new AcademyException(msgS.get("pagamento.no.exists")));

        StatoPagamento completato = statoRep.findById("Completato")
                .orElseThrow(() -> new AcademyException(msgS.get("stato.no.exists")));

        pagamento.setStatoPagamento(completato);
        pagamento.setDataPagamento(LocalDateTime.now());
        pagRep.save(pagamento);
        log.info("Pagamento {} confermato", transazioneId);
    }

    @Override
    @Transactional
    public void markFailed(String transazioneId) throws Exception {
        Pagamenti pagamento = pagRep.findByTransazioneId(transazioneId)
                .orElseThrow(() -> new AcademyException(msgS.get("pagamento.no.exists")));

        StatoPagamento fallito = statoRep.findById("Fallito")
                .orElseThrow(() -> new AcademyException(msgS.get("stato.no.exists")));

        pagamento.setStatoPagamento(fallito);
        pagRep.save(pagamento);
        log.info("Pagamento {} fallito", transazioneId);
    }
}