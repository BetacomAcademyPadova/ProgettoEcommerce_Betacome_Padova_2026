package com.betacom.fe.services.interfaces;

import com.betacom.fe.dto.input.RuoloReq;

public interface IRuoliServices {
	void create(RuoloReq req) throws Exception;
	void delete(Integer idRuolo) throws Exception;

}
