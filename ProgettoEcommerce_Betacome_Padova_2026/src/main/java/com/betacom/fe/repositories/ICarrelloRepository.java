package com.betacom.fe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.Carrello;

public interface ICarrelloRepository extends JpaRepository<Carrello, Integer> {

    Optional<Carrello> findByUserId_UserId(Integer userId);

    boolean existsByUserId_UserId(Integer userId);
}
