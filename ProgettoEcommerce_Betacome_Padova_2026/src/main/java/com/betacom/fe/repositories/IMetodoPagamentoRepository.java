package com.betacom.fe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.MetodoPagamento;

public interface IMetodoPagamentoRepository extends JpaRepository<MetodoPagamento, Integer> {
}