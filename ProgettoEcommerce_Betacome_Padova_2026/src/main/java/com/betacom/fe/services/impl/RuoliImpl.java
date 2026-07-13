package com.betacom.fe.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.RuoloReq;
import com.betacom.fe.dto.output.RuoliDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.RuoliMapper;
import com.betacom.fe.models.Ruoli;
import com.betacom.fe.repositories.IRuoliRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IRuoliServices;
import com.betacom.fe.utils.Normalizzazione;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RuoliImpl implements IRuoliServices{
	private final IRuoliRepository ruoliRep;
    private final IMessaggioServices msgS;
	
	@Override
	@Transactional
	public void create(RuoloReq req) throws Exception {
		String ruolo = Normalizzazione.norm(req.getRuolo());
	    ruoliRep.findByRuolo(ruolo)
	            .ifPresent(r -> {
	            	throw new RuntimeException(msgS.get("role.exists"));
	            });

	    Ruoli r = new Ruoli();
	    r.setRuolo(ruolo);

	    ruoliRep.save(r);
	}

	@Override
	@Transactional
	public void delete(String ruolo) throws Exception {
		Ruoli r = ruoliRep.findByRuolo(ruolo)
				.orElseThrow(() -> new AcademyException(msgS.get("role.no.exists")));
		ruoliRep.delete(r);
	}

	@Override
	public List<RuoliDTO> getAll() throws Exception {
		return ruoliRep.findAll().stream()
                .map(a -> RuoliMapper.toDTO(a))
                .toList();
	}

	@Override
	@Transactional
	public void update(RuoloReq req, Integer idRuolo) throws Exception {
		Ruoli ruolo = ruoliRep.findById(idRuolo)
				.orElseThrow(() -> new AcademyException(msgS.get("ruolo.no.exists")));
		
		ruolo.setRuolo(Normalizzazione.norm(req.getRuolo()));
		ruoliRep.save(ruolo);
		ruoliRep.flush();
	}

}
