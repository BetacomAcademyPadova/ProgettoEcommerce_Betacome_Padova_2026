package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.ProdottiCarrelloDTO;
import com.betacom.fe.models.ProdottiCarrello;

public class ProdottiCarrelloMapper {
	public static ProdottiCarrelloDTO toDTO(ProdottiCarrello riga) {
        Float subtotale = riga.getPrezzo() * riga.getQuantita();

        return ProdottiCarrelloDTO.builder()
            .idRiga(riga.getIdRiga())
            .idCarrello(riga.getCarrello().getIdCarrello())
            .idDivisioneProdotto(riga.getDivisione().getIdDivisione())
            .subtotale(subtotale)
            .build(); 
    }
}
