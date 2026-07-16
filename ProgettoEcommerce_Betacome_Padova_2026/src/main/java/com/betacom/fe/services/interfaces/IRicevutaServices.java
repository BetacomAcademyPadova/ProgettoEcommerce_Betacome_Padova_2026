package com.betacom.fe.services.interfaces;

import java.util.List;

import com.betacom.fe.dto.input.RicevutaReq;
import com.betacom.fe.dto.output.RicevutaDTO;

public interface IRicevutaServices {
	void create(RicevutaReq req) throws Exception;
	void update(RicevutaReq req) throws Exception;
	void delete(Integer idRicevuta) throws Exception;
	
	RicevutaDTO getById(Integer idRicevuta) throws Exception;
	List<RicevutaDTO> getAll() throws Exception;

}
