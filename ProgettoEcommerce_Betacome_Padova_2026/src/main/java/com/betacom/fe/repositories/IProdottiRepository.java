package com.betacom.fe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.Prodotti;


public interface IProdottiRepository extends JpaRepository<Prodotti, Integer>{
	

}
