package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.RuoliDTO;
import com.betacom.fe.models.Ruoli;

public class RuoliMapper {
	public static RuoliDTO toDTO(Ruoli r) {
		return RuoliDTO.builder()
				.ruolo(r.getRuolo())
				.build();
	}
}
