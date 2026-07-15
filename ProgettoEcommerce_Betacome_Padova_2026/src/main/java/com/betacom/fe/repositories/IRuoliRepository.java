package com.betacom.fe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.Ruoli;

public interface IRuoliRepository extends JpaRepository<Ruoli, Integer>{

	Optional<Ruoli> findByRuolo(String ruolo);

}
