package com.betacom.fe.services.interfaces;

import com.betacom.fe.dto.output.CarrelloDTO;

public interface ICarrelloServices {
	CarrelloDTO getById(Integer idCarrello) throws Exception;
	CarrelloDTO getByUser(Integer idUser) throws Exception;
}
