package com.betacom.fe.dto.input;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
	@Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!\\-_])[a-zA-Z0-9@#$%^&+=!\\-_]{8,16}$",
        groups = ValidationGroups.Create.class, message = "user.password.invalid"
    )
    private String password;
}
