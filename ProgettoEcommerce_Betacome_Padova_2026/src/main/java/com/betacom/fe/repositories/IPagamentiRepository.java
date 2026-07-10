package com.betacom.fe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.Pagamenti;

public interface IPagamentiRepository extends JpaRepository<Pagamenti, Integer> {
    Optional<Pagamenti> findByTransazioneId(String transazioneId);
}
