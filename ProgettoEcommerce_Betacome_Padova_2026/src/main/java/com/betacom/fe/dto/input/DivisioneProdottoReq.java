package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DivisioneProdottoReq 
{
	@NotNull(groups = ValidationGroups.Update.class, message = "divProd.no.id")
	private Integer idDivisione;
	
	@NotBlank(groups = ValidationGroups.Create.class, message ="divProd.no.col")
    private String colore;
    
	@NotBlank(groups = ValidationGroups.Create.class, message ="divProd.no.mat")
    private String materiale;

	@NotNull(groups = ValidationGroups.Create.class, message ="divProd.no.alt")
    private Integer altezza;

	@NotNull(groups = ValidationGroups.Create.class, message ="divProd.no.lungh")
    private Integer lunghezza;
    
	@NotNull(groups = ValidationGroups.Create.class, message ="divProd.no.largh")
    private Integer larghezza;
    
	@NotNull(groups = ValidationGroups.Create.class, message ="divProd.no.quant")
    private Integer quantitaDisponibile;
	
	@NotNull(groups = ValidationGroups.Create.class, message ="prodotto.no.id")
	private Integer idProdotto;
}
