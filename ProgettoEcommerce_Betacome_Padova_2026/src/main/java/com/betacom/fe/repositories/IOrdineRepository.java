package com.betacom.fe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.Ordini;

public interface IOrdineRepository extends JpaRepository<Ordini, Integer> {

	List<Ordini> findByUserId_UserId(Integer userId);
}
