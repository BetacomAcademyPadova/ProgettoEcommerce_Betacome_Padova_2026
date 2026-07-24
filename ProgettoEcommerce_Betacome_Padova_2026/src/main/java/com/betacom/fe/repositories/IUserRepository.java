package com.betacom.fe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.User;


public interface IUserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);

}
