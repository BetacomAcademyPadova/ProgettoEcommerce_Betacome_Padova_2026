package com.betacom.fe.dto.input;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarrelloReq {

    @NotNull(groups = ValidationGroups.Update.class, message = "carrello.no.id")
    private Integer idCarrello;

    private LocalDate dataUltimoAgg;

    @NotNull(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message = "user.no.id")
    private Integer userId;
}
