package com.betacom.fe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.Categoria;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer>{

	Optional<Categoria> findByCategoria(String categoria);


}
