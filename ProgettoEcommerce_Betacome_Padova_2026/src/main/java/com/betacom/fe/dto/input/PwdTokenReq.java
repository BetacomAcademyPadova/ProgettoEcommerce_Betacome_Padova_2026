package com.betacom.fe.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwdTokenReq {
	@NotBlank(message = "user.email.req")
    @Email(message = "user.email.inv")
    private String email;
}
