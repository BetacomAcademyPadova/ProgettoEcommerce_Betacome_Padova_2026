package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwdResetterReq {
    @NotBlank(message = "user.token.req")
    private String token;

    @NotBlank(message = "user.password.req")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!\\-_])[a-zA-Z0-9@#$%^&+=!\\-_]{8,16}$", message = "user.password.notvalid")
    private String password;
}