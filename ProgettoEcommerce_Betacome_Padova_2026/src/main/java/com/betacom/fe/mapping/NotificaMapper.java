package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.NotificaDTO;
import com.betacom.fe.dto.output.StatoNotificaDTO;
import com.betacom.fe.models.Notifica;

public class NotificaMapper {

    public static NotificaDTO toDTO(Notifica n) {

        return NotificaDTO.builder()
                .idNotifica(n.getIdNotifica())
                .messaggio(n.getMessaggio())
                .letta(n.getLetta())
                .dataCreazione(n.getDataCreazione())
                .dataScadenza(n.getDataScadenza())
                .statoNotifica(
                        StatoNotificaDTO.builder()
                            .idStato(n.getStatoNotifica().getIdStato())
                            .statoNotifica(n.getStatoNotifica().getStato())
                            .build()
                    )
                .build();
    }
}
