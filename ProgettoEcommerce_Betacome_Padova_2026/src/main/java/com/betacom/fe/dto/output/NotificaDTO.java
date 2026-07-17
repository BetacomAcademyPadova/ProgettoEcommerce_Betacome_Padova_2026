package com.betacom.fe.dto.output;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificaDTO {

    private Integer idNotifica;
    private String messaggio;
    private Boolean letta;
    private LocalDateTime dataCreazione;
}
