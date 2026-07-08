package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LogInReq {

	@NotBlank(groups = ValidationGroups.Login.class , message ="user.username.req")
    private String username;

	@NotBlank(groups = ValidationGroups.Login.class , message ="user.password.req")
    private String password;

}

