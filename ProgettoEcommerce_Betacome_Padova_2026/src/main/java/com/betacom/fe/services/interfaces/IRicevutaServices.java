package com.betacom.fe.services.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.betacom.fe.dto.input.RicevutaReq;
import com.betacom.fe.dto.output.RicevutaDTO;
import com.betacom.fe.models.Ricevuta;

public interface IRicevutaServices {
	void create(RicevutaReq req) throws Exception;
	//void update(RicevutaReq req) throws Exception;
	
	RicevutaDTO getById(Integer idRicevuta) throws Exception;
	//List<RicevutaDTO> getAll() throws Exception;
	//List<RicevutaDTO> getByVenditore(Integer venditoreId) throws Exception;
	List<RicevutaDTO> getByUser(String username) throws Exception;
	List<RicevutaDTO> getByUserAndDateRange(String username, LocalDate dataInizio, LocalDate dataFine) throws Exception;
	List<RicevutaDTO> getRicevuteVenditore(String username) throws Exception;
	List<RicevutaDTO> getRicevuteVenditoreByDateRange(String username, LocalDate dataInizio, LocalDate dataFine) throws Exception;

}
