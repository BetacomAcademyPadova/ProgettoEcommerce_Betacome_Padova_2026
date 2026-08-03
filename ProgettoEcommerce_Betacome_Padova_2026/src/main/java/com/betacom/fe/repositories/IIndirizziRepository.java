package com.betacom.fe.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.fe.models.Indirizzi;
import com.betacom.fe.models.User;

public interface IIndirizziRepository extends JpaRepository<Indirizzi, Integer>{

	List<Indirizzi> findByUserIdUserId(Integer userId);

	Optional<Indirizzi> findByUserIdAndPredefinito(User usr, boolean b);

}
