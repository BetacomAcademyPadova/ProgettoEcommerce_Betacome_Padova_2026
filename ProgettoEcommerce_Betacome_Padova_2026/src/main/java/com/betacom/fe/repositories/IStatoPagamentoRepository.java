package com.betacom.fe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.StatoPagamento;

public interface IStatoPagamentoRepository extends JpaRepository<StatoPagamento, Integer>{

	Optional<StatoPagamento> findByStato_stato(String stato);

}
