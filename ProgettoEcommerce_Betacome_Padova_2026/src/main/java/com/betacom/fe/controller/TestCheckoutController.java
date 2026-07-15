package com.betacom.fe.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import lombok.extern.slf4j.Slf4j;

/**
 * TEMPORARY test-only controller.
 * Creates a PaymentIntent with a hardcoded amount, bypassing the real
 * Ordini/Pagamenti flow, so the checkout UI can be tested before the
 * order flow is wired up.
 *
 * DELETE this class once the real frontend calls
 * PagamentiController#createIntent with a real idOrdine.
 */
@Slf4j
@RestController
@RequestMapping("/rest/test")
public class TestCheckoutController {
	
	@Value("${stripe.publishable.key}")
	private String publishableKey;

    // Hardcoded test amount: change this value directly for now.
    private static final long AMOUNT_IN_CENTS = 4999L; // €49.99
    private static final String CURRENCY = "eur";

    @PostMapping("create-intent-fixed-amount")
    public ResponseEntity<FixedAmountResponse> createFixedAmountIntent() throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(AMOUNT_IN_CENTS)
                .setCurrency(CURRENCY)
                .addPaymentMethodType("card")
                .addPaymentMethodType("satispay")
                .addPaymentMethodType("scalapay")
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        log.info("[TEST] PaymentIntent creato: {}", intent.getId());

        return ResponseEntity.ok(new FixedAmountResponse(intent.getClientSecret(), AMOUNT_IN_CENTS, publishableKey));
    }
    
    @GetMapping("config")
    public ResponseEntity<ConfigResponse> getConfig() {
        return ResponseEntity.ok(new ConfigResponse(publishableKey));
    }

    record ConfigResponse(String publishableKey) {}

    // Small inline response record, just for this test controller
    record FixedAmountResponse(String clientSecret, long amountInCents, String publishableKey) {}
}