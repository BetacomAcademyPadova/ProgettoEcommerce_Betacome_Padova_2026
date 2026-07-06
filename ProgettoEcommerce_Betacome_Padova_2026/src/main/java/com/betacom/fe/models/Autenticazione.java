package com.betacom.fe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name = "autenticazione")
public class Autenticazione {
    @Id
    private String username;

    @Column(nullable=false)
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user")
    private User user;
}
