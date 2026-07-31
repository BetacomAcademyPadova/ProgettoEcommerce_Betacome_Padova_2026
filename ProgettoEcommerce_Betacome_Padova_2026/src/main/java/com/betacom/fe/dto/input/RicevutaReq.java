package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RicevutaReq {
	@NotNull(groups = ValidationGroups.Create.class, message = "numFat.no.present")
    private Integer ordineId;

}
