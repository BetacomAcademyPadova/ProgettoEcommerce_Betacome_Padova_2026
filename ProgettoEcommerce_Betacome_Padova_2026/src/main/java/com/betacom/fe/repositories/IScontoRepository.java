package com.betacom.fe.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betacom.fe.models.Sconto;

public interface IScontoRepository extends JpaRepository<Sconto, Integer>
{	
	@Query("SELECT s FROM Sconto s WHERE s.prodotto.idProdotto = :idProdotto " +
		       "AND (s.dataInizio <= :dataFine AND s.dataFine >= :dataInizio) " +
		       "AND (:idSconto IS NULL OR s.idSconto != :idSconto)")
	List<Sconto> findSovrapposti(
			@Param("idProdotto") Integer idProdotto, 
			@Param("dataInizio") LocalDate dataInizio, 
			@Param("dataFine") LocalDate dataFine,
			@Param("idSconto") Integer idSconto);
	
	@Query("SELECT s FROM Sconto s WHERE s.prodotto.idProdotto = :idProdotto")
	Sconto findByIdProdotto(@Param("idProdotto") Integer idProdotto);
}
