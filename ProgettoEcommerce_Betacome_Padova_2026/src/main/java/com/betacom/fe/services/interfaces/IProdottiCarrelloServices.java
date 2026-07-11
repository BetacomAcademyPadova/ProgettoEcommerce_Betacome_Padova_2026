package com.betacom.fe.services.interfaces;

import java.util.List;

import com.betacom.fe.dto.input.ProdottiCarrelloReq;
import com.betacom.fe.dto.output.ProdottiCarrelloDTO;

public interface IProdottiCarrelloServices {

    void create(ProdottiCarrelloReq req) throws Exception;

    void update(ProdottiCarrelloReq req) throws Exception;

    void delete(Integer idRiga) throws Exception;

    ProdottiCarrelloDTO getById(Integer idRiga) throws Exception;

    List<ProdottiCarrelloDTO> listByCarrello(Integer idCarrello) throws Exception;
}