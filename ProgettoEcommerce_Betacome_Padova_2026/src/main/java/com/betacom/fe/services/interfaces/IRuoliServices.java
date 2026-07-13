package com.betacom.fe.services.interfaces;

import java.util.List;

import com.betacom.fe.dto.input.RuoloReq;
import com.betacom.fe.dto.output.RuoliDTO;

public interface IRuoliServices {
	void create(RuoloReq req) throws Exception;
	List<RuoliDTO> getAll() throws Exception;
	void delete(String idReq) throws Exception;
	void delete(RuoloReq req, Integer idRuolo) throws Exception;

}
