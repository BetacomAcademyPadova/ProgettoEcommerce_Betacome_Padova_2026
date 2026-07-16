package com.betacom.fe.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.AutentiacazioneReq;
import com.betacom.fe.dto.input.LogInReq;
import com.betacom.fe.dto.input.UserReq;
import com.betacom.fe.dto.output.UserDTO;
import com.betacom.fe.models.Autenticazione;
import com.betacom.fe.models.Ruoli;
import com.betacom.fe.models.User;
import com.betacom.fe.repositories.IAutenticazioneRepository;
import com.betacom.fe.repositories.IRuoliRepository;
import com.betacom.fe.repositories.IUserRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IUserServices;
import com.betacom.fe.utils.Normalizzazione;

import jakarta.transaction.Transactional;

import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserImpl implements IUserServices{

	private final IUserRepository repUser;
	private final IRuoliRepository ruoloR;
	private final IAutenticazioneRepository repAut;
    private final IMessaggioServices msgS;
    private final PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public void create(AutentiacazioneReq req) throws Exception {
		User ut = new User();
		ut.setNome(req.getNome());
		ut.setCognome(req.getCognome());
		ut.setEmail(req.getEmail());
		ut.setTelefono(req.getTelefono());
		ut.setRuolo(ruoloR.findByRuolo("User").orElseThrow(() -> new AcademyException(msgS.get("role.no.exists"))));
		ut = repUser.save(ut);
		
		Autenticazione aut = new Autenticazione();
		aut.setUsername(req.getUsername());
		aut.setPassword(passwordEncoder.encode(req.getPassword()));
		aut.setUser(ut);
		repAut.save(aut);
	}

	@Override
	@Transactional
	public void update(UserReq req) throws Exception {
		User usr = repUser.findById(req.getUserId())
				.orElseThrow(() -> new AcademyException(msgS.get("user.no.present")));
		Optional.ofNullable(req.getNome()).ifPresent(t -> usr.setNome(t));
		Optional.ofNullable(req.getCognome()).ifPresent(t -> usr.setCognome(t));
		Optional.ofNullable(req.getEmail()).ifPresent(t -> usr.setEmail(t));
		Optional.ofNullable(req.getTelefono()).ifPresent(t -> usr.setTelefono(t));
		repUser.save(usr);		
	}

	@Override
	@Transactional
	public void delete(Integer idUser) throws Exception {
		User usr = repUser.findById(idUser)
				.orElseThrow(() -> new AcademyException(msgS.get("user.no.present")));
		repUser.delete(usr);
	}

	@Override
	public UserDTO getByUsername(String usr) throws Exception {
		User utente = repUser.findById(repAut.findByUsername(usr)
		                .orElseThrow(() -> new AcademyException(msgS.get("login.error")))
		                .getUser().getUserId()
					).orElseThrow(() -> new AcademyException(msgS.get("user.no.present")));

		 UserDTO dto = new UserDTO();
		 dto.setNome(utente.getNome());
		 dto.setCognome(utente.getCognome());
		 dto.setEmail(utente.getEmail());
		 dto.setTelefono(utente.getTelefono());

		 return dto;
	}

	@Override
	public List<UserDTO> getAll() throws Exception {
		return repUser.findAll()
	            .stream()
	            .map(u -> UserMapper.toDTO(u))
	            .toList();
	}

	@Override
	public UserDTO login(LogInReq req) throws Exception {
	    Autenticazione aut = repAut.findByUsername(req.getUsername())
	            .orElseThrow(() -> new AcademyException(msgS.get("login.error")));

	    if (!passwordEncoder.matches(req.getPassword(), aut.getPassword())) {
	        throw new AcademyException(msgS.get("login.error"));
	    }

	    User usr = aut.getUser();

	    return UserMapper.toDTO(usr);
	}
	
	@Override
	@Transactional
	public void setRuolo(String usr, String ruolo) throws Exception {
		LogInReq req = new LogInReq();
		req.setUsername(usr);
		Autenticazione idUser = repAut.findByUsername(req.getUsername())
	            .orElseThrow(() -> new AcademyException(msgS.get("login.error")));
		User utente = repUser.findById(idUser.getUser().getUserId())
				.orElseThrow(() -> new AcademyException(msgS.get("user.no.present")));
		Ruoli r = ruoloR.findByRuolo(Normalizzazione.norm(ruolo))
				.orElseThrow(() -> new AcademyException(msgS.get("role.no.exists")));
		utente.setRuolo(r);
		repUser.save(utente);
	}
}
