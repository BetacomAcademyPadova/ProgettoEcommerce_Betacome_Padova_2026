package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProdottoReq {

    @NotNull(
        groups = ValidationGroups.Update.class,
        message = "id.no.disp"
    )
    private Integer idProdotto;

    @NotBlank(
        groups = ValidationGroups.Create.class,
        message = "prod.desc.req"
    )
    private String descrizione;

    @NotNull(
        groups = ValidationGroups.Create.class,
        message = "prezzo.no.disp"
    )
    private Float prezzo;

    @NotNull(
        groups = ValidationGroups.Create.class,
        message = "quantita.no.disp"
    )
    private Integer quantitaDisponibile;

    @NotNull(
        groups = ValidationGroups.Create.class,
        message = "stockalert.no.disp"
    )
    private Integer stockAlert;

    @NotNull(
        groups = ValidationGroups.Create.class,
        message = "sottocat.req"
    )
    private Integer idSottoCategoria;

    @NotNull(
        groups = ValidationGroups.Create.class,
        message = "user.req"
    )
    private Integer idUser;
}
