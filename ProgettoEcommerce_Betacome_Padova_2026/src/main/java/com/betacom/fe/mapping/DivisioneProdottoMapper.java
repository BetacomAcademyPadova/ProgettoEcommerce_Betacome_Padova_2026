package com.betacom.fe.mapping;

import java.util.List;

import com.betacom.fe.dto.output.DivisioneProdottoDTO;
import com.betacom.fe.models.DivisioneProdotto;

public class DivisioneProdottoMapper 
{
	public static List<DivisioneProdottoDTO> buildDivProdDTOList(List<DivisioneProdotto> lDivProd)
	{
		return lDivProd.stream()
				.map(divP -> buildDivProdDTO(divP)
						).toList();	
	}
	
	public static DivisioneProdottoDTO buildDivProdDTO(DivisioneProdotto divProd) 
	{
		return 	DivisioneProdottoDTO.builder()
				.idDivisione(divProd.getIdDivisione())
				.colore(divProd.getColore())
				.materiale(divProd.getMateriale())
				.altezza(divProd.getAltezza())
				.lunghezza(divProd.getLunghezza())
				.larghezza(divProd.getLarghezza())
				.quantitaDisponibile(divProd.getQuantitaDisponibile())
				.build();
	}
}
