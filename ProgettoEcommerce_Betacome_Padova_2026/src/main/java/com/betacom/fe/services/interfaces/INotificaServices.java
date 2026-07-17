package com.betacom.fe.services.interfaces;

import java.util.List;

import com.betacom.fe.dto.output.NotificaDTO;
import com.betacom.fe.models.DivisioneProdotto;

public interface INotificaServices {

    void creaStockAlert(DivisioneProdotto divisione) throws Exception;

    List<NotificaDTO> getNonLette(Integer userId) throws Exception;

    void segnaComeLetta(Integer idNotifica) throws Exception;
}
