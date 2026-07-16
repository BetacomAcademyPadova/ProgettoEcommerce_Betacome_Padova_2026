package com.betacom.fe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.MetodoPagamento;
import com.betacom.fe.models.User;

public interface IMetodoPagamentoRepository extends JpaRepository<MetodoPagamento, Integer> {
	List<MetodoPagamento> findByUserId(User userId);
}
