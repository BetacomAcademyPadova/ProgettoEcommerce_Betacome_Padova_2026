package com.betacom.fe.dto.output;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class RicevutaDTO {	
	private Integer idFattura;
    private String numeroFattura;
    private LocalDate dataEmissione;
    private Float imponibile;
    private Float iva;
    private Float totale;
    private List<ProdottiOrdineDTO> prodotti;

}
