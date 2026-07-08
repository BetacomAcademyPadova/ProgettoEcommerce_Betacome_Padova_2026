package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StatoPagReq {

	@NotBlank(groups = ValidationGroups.Create.class, message ="statopag.empty")
	private String statoPag;

}
