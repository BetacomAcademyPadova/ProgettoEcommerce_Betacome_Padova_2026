package com.betacom.fe.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betacom.fe.models.Ordini;

public interface IOrdineRepository extends JpaRepository<Ordini, Integer> {

	List<Ordini> findByUserId_UserIdOrderByDataDesc(Integer userId);
	Optional<Ordini> findFirstByUserId_UserIdAndStato_IdStatoOrderByIdOrdineDesc(
			Integer userId, Integer idStato);
	
	@Query("""
		    SELECT DISTINCT po.ordine
		    FROM ProdottiOrdine po
		    WHERE po.prodotto.venditore.userId = :userId
		    ORDER BY po.ordine.data DESC
		""")
		List<Ordini> findOrdiniVenditore(@Param("userId") Integer userId);
}
