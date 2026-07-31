package com.betacom.fe.services.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.CarrelloReq;
import com.betacom.fe.dto.output.CarrelloDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.CarrelloMapper;
import com.betacom.fe.models.Carrello;
import com.betacom.fe.models.User;
import com.betacom.fe.repositories.ICarrelloRepository;
import com.betacom.fe.repositories.IUserRepository;
import com.betacom.fe.services.interfaces.ICarrelloServices;
import com.betacom.fe.services.interfaces.IMessaggioServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarrelloImpl implements ICarrelloServices {

    private final ICarrelloRepository repCarr;
    private final IUserRepository repUser;
    private final IMessaggioServices msgS;

    @Override
    public CarrelloDTO getById(Integer idCarrello) throws Exception {
        Carrello carr = repCarr.findById(idCarrello)
            .orElseThrow(() -> new AcademyException(msgS.get("carrello.no.id")));

        return CarrelloMapper.toDTO(carr);
    }
    
    @Override
    public CarrelloDTO getByUser(Integer idUser) throws Exception {
        Carrello carr = repCarr.findByUserId_UserId(idUser)
                .orElseThrow(() -> new Exception("carrello.ntfnd"));

        return CarrelloMapper.toDTO(carr);
    }
}