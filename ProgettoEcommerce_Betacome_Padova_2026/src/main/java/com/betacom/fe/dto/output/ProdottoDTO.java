package com.betacom.fe.dto.output;

import java.util.List;

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
	private String immagine;
	private SottoCategoriaDTO sottoCategoria;
	private ScontoDTO sconto;
	private List<DivisioneProdottoDTO> divisioni;
}
