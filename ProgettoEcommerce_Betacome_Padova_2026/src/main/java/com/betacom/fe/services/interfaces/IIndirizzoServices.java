package com.betacom.fe.services.interfaces;

import com.betacom.fe.dto.input.IndirizzoReq;
import com.betacom.fe.dto.output.IndirizzoDTO;

public interface IIndirizzoServices {
	void create(IndirizzoReq req) throws Exception;
	void update(IndirizzoReq req) throws Exception;
	void delete(Integer idIndirizzo) throws Exception;
	IndirizzoDTO getById(Integer idIndirizzo) throws Exception;
}
