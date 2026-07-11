package com.betacom.fe.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.ProdottiCarrello;

public interface IProdottiCarrelloRepository extends JpaRepository<ProdottiCarrello, Integer> {

    List<ProdottiCarrello> findByCarrelloIdCarrello(Integer idCarrello);

    Optional<ProdottiCarrello> findByCarrelloIdCarrelloAndProdottoIdProdotto(Integer idCarrello,Integer idProdotto);

    void deleteByCarrelloIdCarrello(Integer idCarrello);
}
