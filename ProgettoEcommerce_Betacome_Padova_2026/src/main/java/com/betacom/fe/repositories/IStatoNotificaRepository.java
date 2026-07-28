package com.betacom.fe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.StatoNotifica;

public interface IStatoNotificaRepository extends JpaRepository<StatoNotifica, Integer>
{
	Optional<StatoNotifica> findByStato(String stato);
}
