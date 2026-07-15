package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScontoReq 
{
	@NotNull(groups = ValidationGroups.Update.class, message = "sconto.no.id")
    private Integer idSconto;

	@NotNull(groups = ValidationGroups.Create.class, message ="prodotto.no.id")
    private Integer idProdotto;

	@NotNull(groups = ValidationGroups.Create.class, message = "sconto.no.id")
    private Float valore;

	@NotBlank(groups = ValidationGroups.Create.class, message ="sconto.no.data.inizio")
    private String dataInizio;

	@NotBlank(groups = ValidationGroups.Create.class, message ="sconto.no.data.fine")
    private String dataFine;
}
