package com.betacom.fe.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.IndirizzoReq;
import com.betacom.fe.dto.output.IndirizzoDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.IndMapper;
import com.betacom.fe.models.Indirizzi;
import com.betacom.fe.models.User;
import com.betacom.fe.repositories.IIndirizziRepository;
import com.betacom.fe.repositories.IUserRepository;
import com.betacom.fe.services.interfaces.IIndirizzoServices;
import com.betacom.fe.services.interfaces.IMessaggioServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndirizzoImpl implements IIndirizzoServices{
    private final IMessaggioServices msgS;
    private final IIndirizziRepository indR;
    private final IUserRepository userR;

	@Override
	@Transactional
	public void create(IndirizzoReq req) throws Exception {
		User usr = userR.findById(req.getUserId())
				.orElseThrow(()->new AcademyException(msgS.get("user.non.esiste")));
		Indirizzi ind = new Indirizzi();
		ind.setCap(req.getCap());
		ind.setCitta(req.getCitta());
		ind.setPredefinito(req.getPredefinito());
		ind.setVia(req.getVia());
		ind.setUserId(usr);
		
		indR.save(ind);
	}

	@Override
	@Transactional
	public void update(IndirizzoReq req) throws Exception {
		Indirizzi ind = indR.findById(req.getIdIndirizzo())
				.orElseThrow(()->new AcademyException(msgS.get("indirizzo.non.esiste")));

		Optional.ofNullable(req.getCap()).ifPresent(t -> ind.setCap(t));
		Optional.ofNullable(req.getCitta()).ifPresent(t -> ind.setCitta(t));
		Optional.ofNullable(req.getPredefinito()).ifPresent(t -> ind.setPredefinito(t));
		Optional.ofNullable(req.getVia()).ifPresent(t -> ind.setVia(t));
		
		indR.save(ind);
	}

	@Override
	@Transactional
	public void delete(Integer idIndirizzo) throws Exception {
		Indirizzi ind = indR.findById(idIndirizzo)
				.orElseThrow(()->new AcademyException(msgS.get("indirizzo.non.esiste")));
		indR.delete(ind);		
	}
	
	@Override
	public IndirizzoDTO getById(Integer idIndirizzo) throws Exception {
		return IndMapper.toDTO(indR.findById(idIndirizzo)
				 .orElseThrow(() -> new AcademyException(msgS.get("indirizzo.non.esiste"))));
	}

}
