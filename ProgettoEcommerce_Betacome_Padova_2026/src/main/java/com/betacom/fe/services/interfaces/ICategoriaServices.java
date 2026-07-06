package com.betacom.fe.services.interfaces;

import java.util.List;

import com.betacom.fe.dto.input.CategoriaReq;
import com.betacom.fe.dto.output.CategoriaDTO;

public interface ICategoriaServices {
	void create(CategoriaReq req) throws Exception;
	List<CategoriaDTO> getAll() throws Exception;
	void delete(String idCategoria) throws Exception;
}
