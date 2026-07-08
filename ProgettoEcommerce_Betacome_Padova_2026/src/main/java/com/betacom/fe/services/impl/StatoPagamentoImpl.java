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
	    statoPagR.findById(req.getStatoPag())
	    	.orElseThrow(() -> new AcademyException(msgS.get("statopag.exists")));

	    StatoPagamento statoPag = new StatoPagamento();
	    statoPag.setStato(req.getStatoPag());
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
	public void delete(String idStatoPag) throws Exception {
		StatoPagamento statoPag = statoPagR.findById(idStatoPag)
				.orElseThrow(() -> new AcademyException(msgS.get("statopag.no.exists")));
		statoPagR.delete(statoPag);		
	}


}
