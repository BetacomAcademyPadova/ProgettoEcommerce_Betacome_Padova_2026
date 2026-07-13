package com.betacom.fe.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.StatoPagReq;
import com.betacom.fe.dto.output.StatoPagDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.StatoPagMapper;
import com.betacom.fe.models.StatoPagamento;
import com.betacom.fe.repositories.IStatoPagamentoRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IStatoPagamentoServices;
import com.betacom.fe.utils.Normalizzazione;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class StatoPagamentoImpl implements IStatoPagamentoServices{
	private final IStatoPagamentoRepository statoPagR;
    private final IMessaggioServices msgS;
	
	@Override
    @Transactional
	public void create(StatoPagReq req) throws Exception {
    	String stato = Normalizzazione.norm(req.getStatoPag());
	    statoPagR.findByStato(stato)
        .ifPresent(cat -> {
            throw new AcademyException(msgS.get("statoord.exists"));
        });

	    StatoPagamento statoPag = new StatoPagamento();
	    statoPag.setStato(stato);
	    statoPagR.save(statoPag);
	}

	@Override
	public List<StatoPagDTO> getAll() throws Exception {
		return statoPagR.findAll().stream()
                .map(a -> StatoPagMapper.toDTO(a))
                .toList();
	}

	@Override
    @Transactional
	public void delete(String stato) throws Exception {
		StatoPagamento statoPag = statoPagR.findByStato(Normalizzazione.norm(stato))
				.orElseThrow(() -> new AcademyException(msgS.get("statopag.no.exists")));
		statoPagR.delete(statoPag);		
	}

	@Override
    @Transactional
	public void update(StatoPagReq req, Integer stato) throws Exception {
		StatoPagamento statoPag = statoPagR.findById(stato)
				.orElseThrow(() -> new AcademyException(msgS.get("statopag.no.exists")));
		
		statoPag.setStato(Normalizzazione.norm(req.getStatoPag()));
		statoPagR.save(statoPag);
		statoPagR.flush();
	}


}
