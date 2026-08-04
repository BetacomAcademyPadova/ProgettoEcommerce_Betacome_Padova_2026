package com.betacom.fe.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Configurazione pubblica di Stripe esposta al frontend.
 * Contiene solo la publishable key: nessun dato sensibile.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StripeConfigDTO {
    private String publishableKey;
}