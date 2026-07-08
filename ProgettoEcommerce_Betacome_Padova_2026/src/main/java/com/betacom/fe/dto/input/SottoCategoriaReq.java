package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SottoCategoriaReq extends CategoriaReq{
	@NotBlank(groups = ValidationGroups.Create.class, message ="sotcat.empty")
	private String sottoCategoria;
}
