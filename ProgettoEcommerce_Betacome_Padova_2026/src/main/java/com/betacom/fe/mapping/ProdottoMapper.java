package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.ProdottoDTO;
import com.betacom.fe.models.Prodotti;

public class ProdottoMapper {
	public static ProdottoDTO toDTO(Prodotti p) {
		return ProdottoDTO.builder()
				.idProdotto(p.getIdProdotto())
				.descrizione(p.getDescrizione())
				.prezzo(p.getPrezzo())
				.quantitaDisponibile(p.getQuantitaDisponibile())
				.stockAlert(p.getStockAlert())
				.divisioni(DivisioneProdottoMapper.buildDivProdDTOList(p.getDivisioni()))
				.build();
	}

}
