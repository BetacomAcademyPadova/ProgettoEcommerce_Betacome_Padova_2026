package com.betacom.fe.services.interfaces;

import java.util.List;

import com.betacom.fe.dto.input.OrdineReq;
import com.betacom.fe.dto.output.OrdineDTO;

public interface IOrdineServices {

	void create(OrdineReq req) throws Exception;
	OrdineDTO getById(Integer idOrdine) throws Exception;
	List<OrdineDTO> getAll() throws Exception;
	List<OrdineDTO> getAllByUserId(Integer UserId) throws Exception;
}
