package com.betacom.fe.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.betacom.fe.models.Ordini;
import com.betacom.fe.models.ProdottiOrdine;

public interface IProdottiOrdineRepository extends JpaRepository<ProdottiOrdine, Integer> {

	List<ProdottiOrdine> findByOrdine(Ordini ordine);

	// prodotti acquistati da un cliente
	List<ProdottiOrdine> findByOrdine_UserId_UserId(Integer userId);

	// prodotti venduti da un venditore
	List<ProdottiOrdine> findByProdotto_Venditore_UserId(Integer userId);

	List<ProdottiOrdine> findByOrdine_UserId_UserIdAndOrdine_DataBetween(
			Integer userId,
			LocalDate dataInizio,
			LocalDate dataFine);

	List<ProdottiOrdine> findByProdotto_Venditore_UserIdAndOrdine_DataBetween(
			Integer userId,
			LocalDate dataInizio,
			LocalDate dataFine);

	// Prodotti di un singolo ordine del cliente
	@Query("""
			SELECT po
			FROM ProdottiOrdine po
			WHERE po.ordine.idOrdine = :idOrdine
			AND po.ordine.userId.userId = :userId
			""")
	List<ProdottiOrdine> findByOrdineCliente(Integer idOrdine, Integer userId);

	// Prodotti di un singolo ordine visibili al venditore
	@Query("""
			SELECT po
			FROM ProdottiOrdine po
			WHERE po.ordine.idOrdine = :idOrdine
			AND po.prodotto.venditore.userId = :userId
			""")
	List<ProdottiOrdine> findByOrdineVenditore(Integer idOrdine, Integer userId);

}