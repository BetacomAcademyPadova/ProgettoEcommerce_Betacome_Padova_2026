package com.betacom.fe.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable=false)
    private String nome;

    @Column(nullable=false)
    private String cognome;

    @Column(nullable=false)
    private String telefono;

    @OneToMany(mappedBy = "user")
    private List<Indirizzi> indirizzi;
    
    @ManyToOne
    @JoinColumn(name = "ruolo")
    private Ruoli ruolo;
}
