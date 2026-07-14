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
	UserDTO getById(Integer idUSer) throws Exception;
	List<UserDTO> getAll() throws Exception;
	UserDTO login(LogInReq req) throws Exception;
	void setRuolo(Integer idUser, String ruolo) throws Exception;
}
