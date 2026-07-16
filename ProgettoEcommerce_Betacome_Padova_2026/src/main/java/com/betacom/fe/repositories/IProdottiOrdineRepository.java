package com.betacom.fe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.betacom.fe.models.Ordini;
import com.betacom.fe.models.ProdottiOrdine;

public interface IProdottiOrdineRepository extends JpaRepository<ProdottiOrdine, Integer>{
	List<ProdottiOrdine> findByOrdine(Ordini ordine);

}
