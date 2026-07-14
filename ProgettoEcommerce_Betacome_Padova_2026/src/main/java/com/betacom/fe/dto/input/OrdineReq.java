package com.betacom.fe.dto.input;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrdineReq {

    @NotNull(groups = ValidationGroups.Update.class,message = "id.no.disp")
    private Integer idOrdine;

    @NotNull(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class},message = "data.no.disp")
    private LocalDate data;

    @NotNull( groups = {ValidationGroups.Create.class,ValidationGroups.Update.class},message = "totale.no.disp")
    private Float totale;

    @NotNull(groups = {ValidationGroups.Create.class,ValidationGroups.Update.class},message = "user.no.disp")
    private Integer userId;

    @NotNull(groups = {ValidationGroups.Create.class,ValidationGroups.Update.class},message = "indirizzo.fatturazione.no.disp")
    private Integer indirizzoFatturazioneId;

    @NotNull(groups = {ValidationGroups.Create.class,ValidationGroups.Update.class},message = "stato.no.disp")
    private Integer statoId;
}
