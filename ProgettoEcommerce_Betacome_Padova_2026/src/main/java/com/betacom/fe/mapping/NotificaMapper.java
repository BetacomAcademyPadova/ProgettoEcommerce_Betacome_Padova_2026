package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.NotificaDTO;
import com.betacom.fe.models.Notifica;

public class NotificaMapper {

    public static NotificaDTO toDTO(Notifica n) {

        return NotificaDTO.builder()
                .idNotifica(n.getIdNotifica())
                .messaggio(n.getMessaggio())
                .letta(n.getLetta())
                .dataCreazione(n.getDataCreazione())
                .build();
    }
}
