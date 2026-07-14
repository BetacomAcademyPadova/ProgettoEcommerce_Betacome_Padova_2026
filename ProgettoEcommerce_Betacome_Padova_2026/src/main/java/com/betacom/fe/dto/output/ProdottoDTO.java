package com.betacom.fe.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdottoDTO {
	private Integer idProdotto;
	private String descrizione;
	private Float prezzo;
	private Integer quantitaDisponibile;
	private Integer stockAlert;
	
}
