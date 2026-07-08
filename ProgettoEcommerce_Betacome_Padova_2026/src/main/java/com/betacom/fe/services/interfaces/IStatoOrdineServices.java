package com.betacom.fe.services.interfaces;

import java.util.List;

import com.betacom.fe.dto.input.StatoOrdineReq;
import com.betacom.fe.dto.output.StatoOrdineDTO;

public interface IStatoOrdineServices {
	void create(StatoOrdineReq req) throws Exception;
	List<StatoOrdineDTO> getAll() throws Exception;
	void delete(String StatoOrdine) throws Exception;

}
