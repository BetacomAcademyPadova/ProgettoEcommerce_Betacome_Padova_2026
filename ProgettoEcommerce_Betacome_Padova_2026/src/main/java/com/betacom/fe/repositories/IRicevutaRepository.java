package com.betacom.fe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betacom.fe.models.Ricevuta;

@Repository
public interface IRicevutaRepository extends JpaRepository<Ricevuta, Integer>{
	Optional<Ricevuta> findTopByOrderByIdFatturaDesc();

}
