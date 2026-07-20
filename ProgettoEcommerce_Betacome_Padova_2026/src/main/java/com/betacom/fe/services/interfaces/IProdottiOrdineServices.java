package com.betacom.fe.services.interfaces;

import java.util.List;

import com.betacom.fe.dto.input.ProdottiOrdineReq;
import com.betacom.fe.dto.output.ProdottiOrdineDTO;

public interface IProdottiOrdineServices {
	void create(ProdottiOrdineReq req) throws Exception;
	void update(ProdottiOrdineReq req) throws Exception;
    void delete(Integer idItem) throws Exception;
    
    ProdottiOrdineDTO getById(Integer idItem) throws Exception;
	List<ProdottiOrdineDTO> getAll() throws Exception;

}
