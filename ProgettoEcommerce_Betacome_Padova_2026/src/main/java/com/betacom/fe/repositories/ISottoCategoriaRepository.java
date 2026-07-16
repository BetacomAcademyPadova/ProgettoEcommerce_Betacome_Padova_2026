package com.betacom.fe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.Categoria;
import com.betacom.fe.models.SottoCategoria;

public interface ISottoCategoriaRepository extends JpaRepository<SottoCategoria, Integer>{

	Optional<SottoCategoria> findBySottoCategoria(String sotocat);

	Optional<SottoCategoria> findBySottoCategoriaAndCategoria(String sotocat, Categoria categoria);

}
