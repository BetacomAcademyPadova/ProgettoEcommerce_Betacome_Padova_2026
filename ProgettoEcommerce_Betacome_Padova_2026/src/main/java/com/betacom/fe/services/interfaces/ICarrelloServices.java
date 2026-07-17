package com.betacom.fe.services.interfaces;

import com.betacom.fe.dto.input.CarrelloReq;
import com.betacom.fe.dto.output.CarrelloDTO;

public interface ICarrelloServices {
	void create(CarrelloReq req) throws Exception;
	void delete(Integer idCarrello) throws Exception;
	CarrelloDTO getById(Integer idCarrello) throws Exception;
}
