package com.betacom.fe.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.Ordini;

public interface IOrdiniRepository extends JpaRepository<Ordini, Integer>{

}
