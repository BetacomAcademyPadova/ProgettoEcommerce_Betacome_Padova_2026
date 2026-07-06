package com.betacom.fe.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserReq {
	@NotBlank(groups = ValidationGroups.Create.class, message ="user.email.req")
	@Email(message = "user.email.inv")
	private String email;

	@NotBlank(groups = ValidationGroups.Create.class, message ="user.nome.req")
	private String nome;

	@NotBlank(groups = ValidationGroups.Create.class, message ="user.cognome.req")
	private String cognome;

	@NotBlank(groups = ValidationGroups.Create.class, message ="user.telefono.req")
	@Pattern(regexp = "^\\+[0-9]{10, 15}$", message = "user.telefono.notvalid")
	private String telefono;
}
