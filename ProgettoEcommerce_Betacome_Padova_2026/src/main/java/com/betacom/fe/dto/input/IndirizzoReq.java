package com.betacom.fe.dto.input;

import com.betacom.fe.models.User;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IndirizzoReq extends User{
	
	@NotNull(groups = ValidationGroups.Update.class, message = "indirizzo.no.id")
	private Integer idIndirizzo;

	@NotNull(groups = ValidationGroups.Update.class, message = "indirizzo.no.format")
    private String via;

	@NotNull(groups = ValidationGroups.Update.class, message = "indirizzo.no.format")
    private String citta;

	@NotNull(groups = ValidationGroups.Update.class, message = "indirizzo.no.format")
    private String cap;

    private Boolean predefinito;
}
