package com.betacom.fe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.Autenticazione;

public interface IAutenticazioneRepository extends JpaRepository<Autenticazione, Integer>{
	Optional<Autenticazione> findByUserUserId(Integer userId);

	Optional<Autenticazione> findByUsername(String username);
}
