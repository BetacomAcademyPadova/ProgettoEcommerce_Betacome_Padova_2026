package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.OrdineDTO;
import com.betacom.fe.models.Ordini;

public class OrdineMapper {
	public static OrdineDTO toDTO(Ordini o) {
		return OrdineDTO.builder()
				.idOrdine(o.getIdOrdine())
				.totale(o.getTotale())
				.data(o.getData())
				.build();
	}
}
