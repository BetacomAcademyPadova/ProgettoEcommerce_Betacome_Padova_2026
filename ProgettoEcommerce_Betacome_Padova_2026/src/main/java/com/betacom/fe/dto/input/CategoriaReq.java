package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoriaReq {
	@NotBlank(groups = ValidationGroups.Create.class, message ="cat.empty")
	private String categoria;
}
