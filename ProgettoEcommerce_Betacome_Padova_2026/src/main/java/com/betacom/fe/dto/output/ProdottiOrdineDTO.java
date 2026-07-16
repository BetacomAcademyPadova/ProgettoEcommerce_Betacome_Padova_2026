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
public class ProdottiOrdineDTO {
	
	private String prodotto;
    private Integer quantita;
    private Float prezzo;
    private IndirizzoDTO indirizzoSpedizione;

}
