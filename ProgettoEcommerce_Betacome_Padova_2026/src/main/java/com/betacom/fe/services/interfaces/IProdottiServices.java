package com.betacom.fe.services.interfaces;

import java.util.List;

import com.betacom.fe.dto.input.ProdottoReq;
import com.betacom.fe.dto.output.ProdottoDTO;



public interface IProdottiServices {
	void create(ProdottoReq req) throws Exception;
	void update(ProdottoReq req) throws Exception;
	void delete(Integer idProdotto) throws Exception;
	ProdottoDTO getById(Integer idProdotto) throws Exception;
	List<ProdottoDTO> getAll() throws Exception;
	
}
