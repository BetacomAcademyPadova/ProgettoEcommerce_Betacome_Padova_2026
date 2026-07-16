package com.betacom.fe.mapping;

import java.util.List;

import com.betacom.fe.dto.output.ScontoDTO;
import com.betacom.fe.models.Sconto;

public class ScontoMapper 
{
	public static List<ScontoDTO> buildScontoDTOList(List<Sconto> lS)
	{
		return lS.stream()
				.map(s -> buildScontoDTO(s)
						).toList();
	}
	
	public static ScontoDTO buildScontoDTO(Sconto s) 
	{
		return 	ScontoDTO.builder()
				.idSconto(s.getIdSconto())
				.valore(s.getValore())
				.dataInizio(s.getDataInizio())
				.dataFine(s.getDataFine())
				.build();
	}
}
