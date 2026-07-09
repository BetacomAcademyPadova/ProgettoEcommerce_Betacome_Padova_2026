package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.IndirizzoDTO;
import com.betacom.fe.models.Indirizzi;

public class IndMapper {

	public static IndirizzoDTO toDTO(Indirizzi ind) {
		return IndirizzoDTO.builder()
				.via(ind.getVia())
				.citta(ind.getCitta())
				.cap(ind.getCap())
				.predefinito(ind.getPredefinito())
				.build();
	}

}
