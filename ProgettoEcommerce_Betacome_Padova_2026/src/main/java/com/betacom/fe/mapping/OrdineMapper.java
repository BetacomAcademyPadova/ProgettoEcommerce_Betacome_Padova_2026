package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.OrdineDTO;
import com.betacom.fe.models.Ordini;

public class OrdineMapper {
	public static OrdineDTO toDTO(Ordini o) {
		return OrdineDTO.builder()
		        .idOrdine(o.getIdOrdine())
		        .data(o.getData())
		        .totale(o.getTotale())
		        .statoOrdine(o.getStato().getStato())
		        .build();
	}
}
