package com.betacom.fe.services.interfaces;

import java.util.List;

import com.betacom.fe.dto.input.DivisioneProdottoReq;
import com.betacom.fe.dto.output.DivisioneProdottoDTO;

public interface IDivisioneProdottoServices 
{
	void create(DivisioneProdottoReq req) throws Exception;
	void update(DivisioneProdottoReq req) throws Exception;
	void delete(Integer idDivProdotto) throws Exception;
	
	DivisioneProdottoDTO getById(Integer idDivProdotto) throws Exception;
	
	List<DivisioneProdottoDTO> getAll() throws Exception; 
}
