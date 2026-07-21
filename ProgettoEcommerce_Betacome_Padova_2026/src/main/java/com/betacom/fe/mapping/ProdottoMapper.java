package com.betacom.fe.mapping;

import java.util.List;

import com.betacom.fe.dto.input.DivisioneProdottoReq;
import com.betacom.fe.dto.output.ProdottoDTO;
import com.betacom.fe.models.DivisioneProdotto;
import com.betacom.fe.models.Prodotti;
import com.betacom.fe.models.Sconto;

public class ProdottoMapper {
	public static ProdottoDTO buildListByParams(Prodotti p, DivisioneProdottoReq req, Sconto s) 
	{
		List<DivisioneProdotto> divFiltrate = p.getDivisioni().stream()
	            .filter(d -> req.getColore() == null || d.getColore().equalsIgnoreCase(req.getColore()))
	            .filter(d -> req.getMateriale() == null || d.getMateriale().equalsIgnoreCase(req.getMateriale()))
	            .filter(d -> req.getAltezza() == null || d.getAltezza().equals(req.getAltezza()))
	            .filter(d -> req.getLunghezza() == null || d.getLunghezza().equals(req.getLunghezza()))
	            .filter(d -> req.getLarghezza() == null || d.getLarghezza().equals(req.getLarghezza()))
	            .toList();
	
		return ProdottoDTO.builder()
				.idProdotto(p.getIdProdotto())
				.descrizione(p.getDescrizione())
				.prezzo(p.getPrezzo())
				.immagine(p.getImmagine())
				.sottoCategoria(p.getSottoCategoria() != null 
                	? SottoCategoriaMapper.toDTO(p.getSottoCategoria()) 
                	: null)
				.sconto(s != null ? ScontoMapper.buildScontoDTO(s) : null)
				.divisioni(DivisioneProdottoMapper.buildDivProdDTOList(divFiltrate))
				.build();
	}

	public static ProdottoDTO toDTO(Prodotti p, Sconto s) 
	{
		return ProdottoDTO.builder()
				.idProdotto(p.getIdProdotto())
				.descrizione(p.getDescrizione())
				.prezzo(p.getPrezzo())
				.sottoCategoria(SottoCategoriaMapper.toDTO(p.getSottoCategoria()))
				.sconto(s != null ? ScontoMapper.buildScontoDTO(s) : null)
				.divisioni(DivisioneProdottoMapper.buildDivProdDTOList(p.getDivisioni()))
				.build();
	}
}
