package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AutentiacazioneReq extends UserReq{
	@NotBlank(groups = ValidationGroups.Create.class , message ="user.username.req")
    private String username;

	@NotBlank(groups = ValidationGroups.Create.class , message ="user.password.req")
    private String password;
}
