package com.betacom.fe.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.betacom.fe.models.MessageID;
import com.betacom.fe.models.Messaggi;
import com.betacom.fe.repositories.IMessagiRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class MessaggioImpl implements IMessaggioServices{

	private final IMessagiRepository msgR;
	
	@Value("${lang}")
	private String lang;
	
	@Override
	public String get(String code) {
		log.debug("get {}", code);
		String r = null;
		Optional<Messaggi> m = msgR.findById(new MessageID(lang, code));
		if (m.isEmpty())
			r = code;
		else
			r = m.get().getMessagio();
		
		
		return r;
	}

}
