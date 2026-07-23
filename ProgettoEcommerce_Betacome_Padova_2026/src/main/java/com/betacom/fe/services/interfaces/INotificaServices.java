package com.betacom.fe.services.interfaces;

import java.util.List;

import com.betacom.fe.dto.output.NotificaDTO;
import com.betacom.fe.models.DivisioneProdotto;

public interface INotificaServices {

    void creaStockAlert(DivisioneProdotto divisione) throws Exception;
    
    void inviaRichiesta(Integer userId, String messaggio) throws Exception;

    List<NotificaDTO> getNonLette(Integer userId) throws Exception;
    List<NotificaDTO> getTutteNonLette() throws Exception;

    void segnaComeLetta(Integer idNotifica) throws Exception;
}
