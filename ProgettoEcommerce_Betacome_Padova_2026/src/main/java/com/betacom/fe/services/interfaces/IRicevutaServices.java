package com.betacom.fe.services.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.betacom.fe.dto.input.RicevutaReq;
import com.betacom.fe.dto.output.RicevutaDTO;

public interface IRicevutaServices {

    void create(RicevutaReq req) throws Exception;

    RicevutaDTO getById(Integer idRicevuta) throws Exception;

    List<RicevutaDTO> getByUserId(Integer userId) throws Exception;

    List<RicevutaDTO> getByUserIdAndDateRange(Integer userId,
            LocalDate dataInizio,
            LocalDate dataFine
    ) throws Exception;

    List<RicevutaDTO> getRicevuteVenditore(Integer venditoreId) throws Exception;

    List<RicevutaDTO> getRicevuteVenditoreByDateRange(Integer venditoreId,
            LocalDate dataInizio,
            LocalDate dataFine
    ) throws Exception;

}