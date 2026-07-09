package com.betacom.fe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.Indirizzi;

public interface IIndirizziRepository extends JpaRepository<Indirizzi, Integer>{

	List<Indirizzi> findByUserIdUserId(Integer userId);

}
