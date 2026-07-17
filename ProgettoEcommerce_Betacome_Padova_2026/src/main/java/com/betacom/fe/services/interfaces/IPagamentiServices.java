package com.betacom.fe.services.interfaces;

import com.stripe.exception.StripeException;

import java.util.List;

import com.betacom.fe.dto.input.PaymentIntentReq;
import com.betacom.fe.dto.output.MetodoPagamentoDTO;
import com.betacom.fe.dto.output.PaymentIntentDTO;

public interface IPagamentiServices {
    PaymentIntentDTO createPaymentIntent(PaymentIntentReq req) throws Exception, StripeException;
    void markSucceeded(String transazioneId, String paymentMethodId) throws Exception;
    void markFailed(String transazioneId, String paymentMethodId) throws Exception;
    List<MetodoPagamentoDTO> getMetodiSalvatiByOrdine(Integer idOrdine) throws Exception;
}