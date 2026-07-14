package com.betacom.fe.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
	private Integer userId;
	private String email;
	private String nome;
	private String cognome;
	private String telefono;
	private String ruolo;
}
