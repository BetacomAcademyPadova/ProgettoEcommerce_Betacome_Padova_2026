package com.betacom.fe.services.interfaces;

import java.util.List;


import com.betacom.fe.dto.input.SottoCategoriaReq;
import com.betacom.fe.dto.output.SottoCategoriaDTO;

public interface ISottoCategoriaServices {
	void create(SottoCategoriaReq req) throws Exception;
	List<SottoCategoriaDTO> getAll() throws Exception;
	void delete(Integer id) throws Exception;
	void update(SottoCategoriaReq req, Integer id) throws Exception;

}
