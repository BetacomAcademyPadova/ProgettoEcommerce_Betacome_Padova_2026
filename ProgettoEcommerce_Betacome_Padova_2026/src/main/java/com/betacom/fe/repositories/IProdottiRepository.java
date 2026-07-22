package com.betacom.fe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betacom.fe.models.Prodotti;

public interface IProdottiRepository extends JpaRepository<Prodotti, Integer>
{	
	@Query(value = "SELECT DISTINCT p.* FROM prodotti p " +
            "LEFT JOIN divisione_prodotto d ON p.id_prodotto = d.id_prodotto " +
			"LEFT JOIN sconti sc ON p.id_prodotto = sc.prodotto " +
			"JOIN sotto_categoria s ON p.sottocategoria = s.id_sotto_categoria " +
			"WHERE (:descrizione IS NULL OR p.descrizione = :descrizione) " +
			"AND (:prezzo IS NULL OR p.prezzo = :prezzo) " +
			"AND (:sotto_categoria IS NULL OR s.sotto_categoria = :sotto_categoria) " +
            "AND (:colore IS NULL OR d.colore = :colore) " +
            "AND (:materiale IS NULL OR d.materiale = :materiale) " +
            "AND (:altezza IS NULL OR d.altezza = :altezza) " +
            "AND (:lunghezza IS NULL OR d.lunghezza = :lunghezza) " +
            "AND (:larghezza IS NULL OR d.larghezza = :larghezza) ",    
            nativeQuery = true)
	List<Prodotti> findByFiltri(
			@Param("descrizione") String descrizione,
			@Param("prezzo") Float prezzo,
			@Param("colore") String colore, 
			@Param("sotto_categoria") String sottocategoria,
            @Param("materiale") String materiale, 
            @Param("altezza") Integer altezza,
            @Param("lunghezza") Integer lunghezza,
            @Param("larghezza") Integer larghezza);
	
	@Query(value = "SELECT DISTINCT p.* FROM prodotti p " +
            "LEFT JOIN divisione_prodotto d ON p.id_prodotto = d.id_prodotto " +
			"JOIN sconti sc ON p.id_prodotto = sc.prodotto " +
			"JOIN sotto_categoria s ON p.sottocategoria = s.id_sotto_categoria " +
			"WHERE (:descrizione IS NULL OR p.descrizione = :descrizione) " +
			"AND (:prezzo IS NULL OR p.prezzo = :prezzo) " +
			"AND (:sotto_categoria IS NULL OR s.sotto_categoria = :sotto_categoria) " +
            "AND (:colore IS NULL OR d.colore = :colore) " +
            "AND (:materiale IS NULL OR d.materiale = :materiale) " +
            "AND (:altezza IS NULL OR d.altezza = :altezza) " +
            "AND (:lunghezza IS NULL OR d.lunghezza = :lunghezza) " +
            "AND (:larghezza IS NULL OR d.larghezza = :larghezza) ",    
            nativeQuery = true)
	List<Prodotti> findByFiltriESconti(
			@Param("descrizione") String descrizione,
			@Param("prezzo") Float prezzo,
			@Param("colore") String colore, 
			@Param("sotto_categoria") String sottocategoria,
            @Param("materiale") String materiale, 
            @Param("altezza") Integer altezza,
            @Param("lunghezza") Integer lunghezza,
            @Param("larghezza") Integer larghezza);
}
