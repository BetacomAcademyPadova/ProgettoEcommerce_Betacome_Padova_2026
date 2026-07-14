package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.UserDTO;
import com.betacom.fe.models.User;

public class UserMapper {
	public static UserDTO toDTO(User u) {
		return UserDTO.builder()
				.userId(u.getUserId())
				.nome(u.getNome())
				.cognome(u.getCognome())
				.email(u.getEmail())
				.telefono(u.getTelefono())
				.ruolo(u.getRuolo().getRuolo())
				.build();
	}
}