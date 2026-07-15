package com.betacom.fe.services.interfaces;

import com.stripe.exception.StripeException;

import com.betacom.fe.dto.input.PaymentIntentReq;
import com.betacom.fe.dto.output.PaymentIntentDTO;

public interface IPagamentiServices {
    PaymentIntentDTO createPaymentIntent(PaymentIntentReq req) throws Exception, StripeException;
    void markSucceeded(String transazioneId, String paymentMethodId) throws Exception;
    void markFailed(String transazioneId) throws Exception;
}