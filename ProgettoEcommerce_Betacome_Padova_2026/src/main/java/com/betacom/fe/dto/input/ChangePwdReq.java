package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePwdReq {

    @NotBlank
    private String username;

    @NotBlank
    private String oldPassword;

	@NotBlank(groups = ValidationGroups.Login.class , message ="user.password.req")
    @Pattern(
            groups = ValidationGroups.Login.class,
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._#\\-])[A-Za-z\\d@$!%*?&._#\\-]{8,16}$",
            message = "user.password.pattern"
        )
	private String newPassword;
}
