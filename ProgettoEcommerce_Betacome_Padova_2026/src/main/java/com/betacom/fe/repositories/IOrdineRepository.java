package com.betacom.fe.repositories;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.Ordini;

public interface IOrdineRepository extends JpaRepository<Ordini, Integer> {

	List<Ordini> findByUserId_UserId(Integer userId);
	
	// ordine piu' recente dell'utente in un certo stato: serve per riusare
	// l'ordine "in attesa di pagamento" invece di crearne uno nuovo
	Optional<Ordini> findFirstByUserId_UserIdAndStato_IdStatoOrderByIdOrdineDesc(
			Integer userId, Integer idStato);
}
