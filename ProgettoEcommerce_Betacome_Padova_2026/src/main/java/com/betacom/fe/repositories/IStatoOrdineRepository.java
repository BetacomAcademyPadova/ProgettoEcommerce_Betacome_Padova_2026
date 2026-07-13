package com.betacom.fe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.StatoOrdine;

public interface IStatoOrdineRepository extends JpaRepository<StatoOrdine, Integer>{

	Optional<StatoOrdine> findByStato(String stato);

}
