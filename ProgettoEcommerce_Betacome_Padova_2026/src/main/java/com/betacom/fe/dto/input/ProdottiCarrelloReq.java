package com.betacom.fe.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProdottiCarrelloReq {
    @NotNull(groups = ValidationGroups.Update.class, message = "prodotti.carrello.no.id")
    private Integer idRiga;

    @NotNull(groups = ValidationGroups.Create.class, message = "carrello.no.id")
    private Integer idCarrello;

    @NotNull(groups = ValidationGroups.Create.class, message = "prodotto.no.id")
    private Integer idProdotto;

    @NotNull(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message = "quantita.null")
    @Min(value = 1, groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message = "quantita.non.valida")
    private Integer quantita;
}