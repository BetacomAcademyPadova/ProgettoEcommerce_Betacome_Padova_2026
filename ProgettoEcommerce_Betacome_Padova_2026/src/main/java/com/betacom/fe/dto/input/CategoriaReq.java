package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoriaReq {
	private Integer idCategoria;
	@NotBlank(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class}, message ="cat.empty")
	private String categoria;
}
