package com.betacom.fe.services.interfaces;

import java.util.List;


import com.betacom.fe.dto.input.StatoPagReq;
import com.betacom.fe.dto.output.StatoPagDTO;

public interface IStatoPagamentoServices {
	void create(StatoPagReq req) throws Exception;
	List<StatoPagDTO> getAll() throws Exception;
	void delete(String idStato) throws Exception;
	void update(StatoPagReq req, Integer stato) throws Exception;

}
