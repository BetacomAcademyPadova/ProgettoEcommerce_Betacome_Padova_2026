package com.betacom.fe.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betacom.fe.models.Ricevuta;

@Repository
public interface IRicevutaRepository extends JpaRepository<Ricevuta, Integer>{

    Optional<Ricevuta> findTopByOrderByIdFatturaDesc();

    List<Ricevuta> findByOrdineUserIdUserIdOrderByDataEmissioneDesc(Integer userId);


    List<Ricevuta> findByOrdineUserIdUserIdAndDataEmissioneBetween(
            Integer userId,
            LocalDate dataInizio,
            LocalDate dataFine
    );

    List<Ricevuta> findByVenditoreUserIdOrderByDataEmissioneDesc(
            Integer userId
    );
    
    List<Ricevuta> findByVenditoreUserIdAndDataEmissioneBetween(
            Integer userId,
            LocalDate dataInizio,
            LocalDate dataFine
    );

}