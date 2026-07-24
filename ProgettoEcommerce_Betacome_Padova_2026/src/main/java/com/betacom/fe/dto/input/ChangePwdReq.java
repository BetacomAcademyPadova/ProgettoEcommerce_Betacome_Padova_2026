package com.betacom.fe.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePwdReq {

    @NotBlank(groups = {ValidationGroups.ChgUsername.class, ValidationGroups.ChgPwd.class} , message ="user.username.req")
    private String username;

    @NotBlank(groups = ValidationGroups.ChgUsername.class, message ="user.username.req")
    private String newUsername;
    
    @NotBlank(groups = ValidationGroups.ChgPwd.class , message ="user.password.req")
    private String oldPassword;

	@NotBlank(groups = ValidationGroups.ChgPwd.class , message ="user.password.req")
    @Pattern(
            groups = ValidationGroups.ChgPwd.class,
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._#\\-])[A-Za-z\\d@$!%*?&._#\\-]{8,16}$",
            message = "user.password.pattern"
        )
	private String newPassword;
}
