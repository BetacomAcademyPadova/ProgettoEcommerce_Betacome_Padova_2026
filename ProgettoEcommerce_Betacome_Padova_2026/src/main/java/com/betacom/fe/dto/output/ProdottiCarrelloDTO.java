package com.betacom.fe.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdottiCarrelloDTO {

    private Integer idRiga;
    private Integer idCarrello;
    private Integer idDivisioneProdotto;
    private Integer quantita;
    private Float prezzo;
    private Float subtotale;
}
