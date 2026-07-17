package com.betacom.fe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.Notifica;

public interface INotificaRepository
        extends JpaRepository<Notifica, Integer> {

	List<Notifica> findByUser_UserIdAndLettaFalse(Integer userId);
}
