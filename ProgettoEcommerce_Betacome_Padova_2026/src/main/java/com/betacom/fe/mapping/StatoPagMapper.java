package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.StatoPagDTO;
import com.betacom.fe.models.StatoPagamento;

public class StatoPagMapper {
	public static StatoPagDTO toDTO(StatoPagamento r) {
		return StatoPagDTO.builder()
				.statoPag(r.getStato())
				.build();
	}
}
