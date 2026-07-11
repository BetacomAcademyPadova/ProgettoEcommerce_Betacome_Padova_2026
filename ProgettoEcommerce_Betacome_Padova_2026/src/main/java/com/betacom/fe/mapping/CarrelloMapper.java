package com.betacom.fe.mapping;

import java.util.List;

import com.betacom.fe.dto.output.CarrelloDTO;
import com.betacom.fe.dto.output.ProdottiCarrelloDTO;
import com.betacom.fe.models.Carrello;

public class CarrelloMapper {
	public static CarrelloDTO toDTO(Carrello carr) {
		List<ProdottiCarrelloDTO> prodotti = carr.getProdotti() == null ? List.of() : carr.getProdotti()
            .stream()
            .map(ProdottiCarrelloMapper::toDTO)
            .toList();

        float totale = prodotti.stream()
            .map(ProdottiCarrelloDTO::getSubtotale)
            .reduce(0F, Float::sum);
		
		return CarrelloDTO.builder()
			.idCarrello(carr.getIdCarrello())
			.dataUltimoAgg(carr.getDataUltimoAgg())
			.user(UserMapper.toDTO(carr.getUserId()))
			.prodotti(prodotti)
            .totale(totale)
			.build();
	}
}
