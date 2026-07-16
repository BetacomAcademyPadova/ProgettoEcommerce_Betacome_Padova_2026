package com.betacom.fe.dto.input;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RicevutaReq {
	@NotNull(groups = ValidationGroups.Update.class, message = "ricevuta.no.id")
	private Integer idFattura;
	@NotNull(groups = ValidationGroups.Create.class, message = "numFat.no.present")
    private Integer ordineId;
	@NotNull(groups = ValidationGroups.Create.class, message = "numFat.no.present")
    private String numeroFattura;
	@NotNull(groups = ValidationGroups.Create.class, message = "fat.no.date")
    private LocalDate dataEmissione;
    private Float imponibile;
    private Float iva;
    private Float totale;

}
