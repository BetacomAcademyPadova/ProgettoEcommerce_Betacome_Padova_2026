package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.ProdottiOrdineDTO;
import com.betacom.fe.models.ProdottiOrdine;

public class ProdottiOrdineMapper {
	public static ProdottiOrdineDTO toDTO(ProdottiOrdine o) {
		return ProdottiOrdineDTO.builder()
				.quantita(o.getQuantita())
				.prezzo(o.getPrezzo())
				.indirizzoSpedizione(IndMapper.toDTO(o.getIndirizzoSpedizione()))
				.prodotto(o.getProdotto().getDescrizione())
				.build();
	}

}
