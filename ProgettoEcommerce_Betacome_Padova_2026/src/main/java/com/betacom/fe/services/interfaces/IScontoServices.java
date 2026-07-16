package com.betacom.fe.services.interfaces;

import java.util.List;

import com.betacom.fe.dto.input.ScontoReq;
import com.betacom.fe.dto.output.ScontoDTO;

public interface IScontoServices 
{
	void create(ScontoReq req) throws Exception;
	void update(ScontoReq req) throws Exception;
	void delete(Integer idSconto) throws Exception;
	
	ScontoDTO getById(Integer idSconto) throws Exception;
	List<ScontoDTO> getAll() throws Exception;
}
