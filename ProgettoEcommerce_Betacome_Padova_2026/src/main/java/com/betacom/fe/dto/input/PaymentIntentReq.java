package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentIntentReq {
    @NotNull(groups = ValidationGroups.Create.class, message = "ordine.empty")
    private Integer idOrdine;
    private Boolean salvaMetodo;
}