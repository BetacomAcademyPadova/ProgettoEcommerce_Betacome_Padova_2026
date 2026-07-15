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
public class DivisioneProdottoDTO 
{
	private Integer idDivisione;
    private String colore;
    private String materiale;
    private Integer altezza;
    private Integer lunghezza;
    private Integer larghezza;
    private Integer quantitaDisponibile;
}
