package com.betacom.fe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betacom.fe.models.Immagini;

@Repository
public interface IImmaginiRepository extends JpaRepository<Immagini, Integer>{

    List<Immagini> findByProdottoIdProdotto(Integer idProdotto);

}
