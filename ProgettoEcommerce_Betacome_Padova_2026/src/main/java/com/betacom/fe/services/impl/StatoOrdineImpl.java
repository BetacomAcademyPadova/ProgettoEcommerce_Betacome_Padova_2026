package com.betacom.fe.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.StatoOrdineReq;
import com.betacom.fe.dto.output.StatoOrdineDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.StatoOrdineMapper;
import com.betacom.fe.models.StatoOrdine;
import com.betacom.fe.repositories.IStatoOrdineRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IStatoOrdineServices;
import com.betacom.fe.utils.Normalizzazione;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class StatoOrdineImpl implements IStatoOrdineServices{
	private final IStatoOrdineRepository statoOrdineR;
    private final IMessaggioServices msgS;
	
	@Override
    @Transactional
	public void create(StatoOrdineReq req) throws Exception {
    	String stato = Normalizzazione.norm(req.getStato());
    	statoOrdineR.findByStato_stato(stato)
	    	.orElseThrow(() -> new AcademyException(msgS.get("statoord.exists")));

	    StatoOrdine statoOrd = new StatoOrdine();
	    statoOrd.setStato(stato);
	    statoOrdineR.save(statoOrd);
	}

	@Override
	public List<StatoOrdineDTO> getAll() throws Exception {
		return statoOrdineR.findAll().stream()
                .map(a -> StatoOrdineMapper.toDTO(a))
                .toList();
	}

	@Override
    @Transactional
	public void delete(String stato) throws Exception {
		StatoOrdine statoOrd = statoOrdineR.findByStato_stato(Normalizzazione.norm(stato))
				.orElseThrow(() -> new AcademyException(msgS.get("statoord.no.exists")));
		statoOrdineR.delete(statoOrd);		
	}

	@Override
    @Transactional
	public void update(StatoOrdineReq req, Integer stato) throws Exception {
		StatoOrdine statoOrd = statoOrdineR.findById(stato)
				.orElseThrow(() -> new AcademyException(msgS.get("statoord.no.exists")));
		
		statoOrd.setStato(Normalizzazione.norm(req.getStato()));
		statoOrdineR.save(statoOrd);
		statoOrdineR.flush();
	}


}
