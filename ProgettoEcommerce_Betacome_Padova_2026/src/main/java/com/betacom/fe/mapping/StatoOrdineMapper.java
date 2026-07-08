package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.StatoOrdineDTO;
import com.betacom.fe.models.StatoOrdine;

public class StatoOrdineMapper {
	public static StatoOrdineDTO toDTO(StatoOrdine r) {
		return StatoOrdineDTO.builder()
				.statoOrdine(r.getStato())
				.build();
	}
}
