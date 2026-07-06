package com.betacom.fe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.MessageID;
import com.betacom.fe.models.Messaggi;

public interface IMessagiRepository extends JpaRepository<Messaggi, MessageID>{

}
