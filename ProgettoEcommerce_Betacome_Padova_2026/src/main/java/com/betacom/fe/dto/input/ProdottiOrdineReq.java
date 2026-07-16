package com.betacom.fe.dto.input;

import com.betacom.fe.models.Indirizzi;
import com.betacom.fe.models.Ordini;
import com.betacom.fe.models.Prodotti;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProdottiOrdineReq {
	
	@NotNull(groups = ValidationGroups.Update.class, message = "prodottiord.no.id")
	private Integer idItem;
	@NotNull(groups = ValidationGroups.Create.class, message = "prodottiord.no.ord")
	private Integer ordineId;
	@NotNull(groups = ValidationGroups.Create.class, message = "prodottiord.no.pro")
	private Integer prodottoId;
	@NotNull(groups = ValidationGroups.Create.class, message = "prodottiord.no.procar")
	private Integer prodottiCarrelloId;
	@NotNull(groups = ValidationGroups.Create.class, message = "prodottiord.no.indirizzo")
    private Integer indirizzoSpedizioneId;
	@NotNull(groups = ValidationGroups.Create.class, message = "prodottiord.no.divord")
	private Integer divisioneOrdineId;

}
