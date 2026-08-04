package com.betacom.fe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.fe.dto.input.PaymentIntentReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.PaymentIntentDTO;
import com.betacom.fe.dto.output.StripeConfigDTO;
import com.betacom.fe.services.interfaces.IPagamentiServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/Pagamenti")
public class PagamentiController {

    private final IPagamentiServices pagS;

    @Value("${stripe.publishable.key}")
    private String publishableKey;

    @GetMapping("config")
    public ResponseEntity<StripeConfigDTO> getConfig() {
        return ResponseEntity.ok(
                StripeConfigDTO.builder()
                        .publishableKey(publishableKey)
                        .build());
    }

    @PostMapping("create-intent")
    public ResponseEntity<PaymentIntentDTO> createIntent(
            @RequestBody(required = true) @Validated(ValidationGroups.Create.class) PaymentIntentReq req)
            throws Exception {
        log.debug("create-intent per ordine {}", req.getIdOrdine());
        return ResponseEntity.ok(pagS.createPaymentIntent(req));
    }

}