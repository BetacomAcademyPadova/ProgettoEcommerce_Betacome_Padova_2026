package com.betacom.fe.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betacom.fe.models.ResetToken;
import com.betacom.fe.models.User;

@Repository
public interface IPwdResetRepository extends JpaRepository<ResetToken, UUID>{
    Optional<ResetToken> findByUser(User user);
}
