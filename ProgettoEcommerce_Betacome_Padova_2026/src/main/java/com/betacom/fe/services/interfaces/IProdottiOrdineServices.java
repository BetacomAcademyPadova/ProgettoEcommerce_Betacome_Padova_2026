package com.betacom.fe.services.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.betacom.fe.dto.input.ProdottiOrdineReq;
import com.betacom.fe.dto.output.ProdottiOrdineDTO;

public interface IProdottiOrdineServices {

    void create(ProdottiOrdineReq req) throws Exception;

    ProdottiOrdineDTO getById(Integer idItem) throws Exception;

    List<ProdottiOrdineDTO> getAll() throws Exception;

    List<ProdottiOrdineDTO> getByCliente(Integer userId) throws Exception;

    List<ProdottiOrdineDTO> getByCliente(
            Integer userId,
            LocalDate dataInizio,
            LocalDate dataFine
    ) throws Exception;

    List<ProdottiOrdineDTO> getByVenditore(Integer userId) throws Exception;

    List<ProdottiOrdineDTO> getByVenditore(
            Integer userId,
            LocalDate dataInizio,
            LocalDate dataFine
    ) throws Exception;

}