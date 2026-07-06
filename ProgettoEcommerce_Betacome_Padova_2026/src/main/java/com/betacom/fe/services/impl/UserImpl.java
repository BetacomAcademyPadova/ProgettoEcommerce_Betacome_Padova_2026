package com.betacom.fe.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.UserReq;
import com.betacom.fe.dto.output.UserDTO;
import com.betacom.fe.services.interfaces.IUserServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserImpl implements IUserServices{

	@Override
	public void create(UserReq req) throws Exception {
		
	}

	@Override
	public void update(UserReq req) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer idUser) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserDTO getById(Integer idUSer) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
