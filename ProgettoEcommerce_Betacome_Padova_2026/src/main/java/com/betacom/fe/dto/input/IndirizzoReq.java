package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IndirizzoReq {
	
	@NotNull(groups = ValidationGroups.Update.class, message = "indirizzo.no.id")
	private Integer idIndirizzo;

	@NotNull(groups = ValidationGroups.Create.class, message = "indirizzo.no.iduser")
	private Integer idUser;
	
	@NotNull(groups = ValidationGroups.Create.class, message = "indirizzo.no.format")
    private String via;

	@NotNull(groups = ValidationGroups.Create.class, message = "indirizzo.no.format")
    private String citta;

	@NotNull(groups = ValidationGroups.Create.class, message = "indirizzo.no.format")
    private String cap;

    private Boolean predefinito;
}
