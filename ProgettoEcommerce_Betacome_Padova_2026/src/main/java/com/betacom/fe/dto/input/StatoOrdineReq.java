package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StatoOrdineReq {
	@NotBlank(groups = {ValidationGroups.Create.class,ValidationGroups.Update.class} , message ="stato.order.req")
	private String stato;
}
