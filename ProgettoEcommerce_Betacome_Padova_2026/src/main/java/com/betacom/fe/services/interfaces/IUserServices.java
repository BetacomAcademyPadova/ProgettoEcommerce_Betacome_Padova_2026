package com.betacom.fe.services.interfaces;

import java.util.List;

import com.betacom.fe.dto.input.AutentiacazioneReq;
import com.betacom.fe.dto.input.LogInReq;
import com.betacom.fe.dto.input.UserReq;
import com.betacom.fe.dto.output.UserDTO;

public interface IUserServices {
	void create(AutentiacazioneReq req) throws Exception;
	void update(UserReq req) throws Exception;
	void delete(Integer idUser) throws Exception;
	List<UserDTO> getAll() throws Exception;
	UserDTO login(LogInReq req) throws Exception;
	void setRuolo(String usr, String ruolo) throws Exception;
	UserDTO getByUsername(String usr) throws Exception;
}
