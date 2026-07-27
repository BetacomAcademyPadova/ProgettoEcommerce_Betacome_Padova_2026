package com.betacom.fe.dto.input;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ImmaginiReq {

    @NotBlank(groups = ValidationGroups.Create.class, message = "files.no.disp")
    private MultipartFile[] files;

    @NotBlank(groups = ValidationGroups.Create.class, message = "id.no.disp")
    private Integer id;
}