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

    @Transactional
    @Override
    public void create(CarrelloReq req) throws Exception {
        User usr = repUser.findById(req.getUserId())
            .orElseThrow(() -> new AcademyException(msgS.get("user.non.esiste")));

        if (repCarr.existsByUserId(req.getUserId()))
            throw new AcademyException(msgS.get("carrello.user.esiste"));
        
        Carrello carr = new Carrello();
        carr.setDataUltimoAgg(LocalDate.now());
        carr.setUserId(usr);

        repCarr.save(carr);
    }

    @Transactional
    @Override
    public void update(CarrelloReq req) throws Exception {
        Carrello carr = repCarr.findById(req.getIdCarrello())
            .orElseThrow(() -> new AcademyException(msgS.get("carrello.non.esiste")));

        User usr = repUser.findById(req.getUserId())
            .orElseThrow(() -> new AcademyException(msgS.get("user.non.esiste")));

        Carrello altroCarrello = repCarr.findByUserId(req.getUserId()).orElse(null);

        if (altroCarrello != null && !altroCarrello.getIdCarrello().equals(carr.getIdCarrello()))
            throw new AcademyException(msgS.get("carrello.user.esiste"));
        
        carr.setUserId(usr);
        carr.setDataUltimoAgg(LocalDate.now());

        repCarr.save(carr);
    }

    @Transactional
    @Override
    public void delete(Integer idCarrello) throws Exception {

        Carrello carr = repCarr.findById(idCarrello)
            .orElseThrow(() -> new AcademyException(msgS.get("carrello.non.esiste")));

        repCarr.delete(carr);
    }

    @Override
    public CarrelloDTO getById(Integer idCarrello) throws Exception {

        Carrello carr = repCarr.findById(idCarrello)
            .orElseThrow(() -> new AcademyException(msgS.get("carrello.non.esiste")));

        return CarrelloMapper.toDTO(carr);
    }
}