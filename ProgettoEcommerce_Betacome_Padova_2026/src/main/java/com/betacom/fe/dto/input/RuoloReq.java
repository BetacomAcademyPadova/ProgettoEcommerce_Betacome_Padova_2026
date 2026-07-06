package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RuoloReq {
	@NotBlank(groups = ValidationGroups.Create.class, message ="ruolo.user.req")
	private String ruolo;
}
