package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.RicevutaDTO;
import com.betacom.fe.models.Ricevuta;

public class RicevutaMapper {
	
	public static RicevutaDTO toDTO(Ricevuta r) {

	    return RicevutaDTO.builder()
	            .idFattura(r.getIdFattura())
	            .numeroFattura(r.getNumeroFattura())
	            .dataEmissione(r.getDataEmissione())
	            .imponibile(r.getImponibile())
	            .iva(r.getIva())
	            .totale(r.getTotale())
	            .build();
	}

}
