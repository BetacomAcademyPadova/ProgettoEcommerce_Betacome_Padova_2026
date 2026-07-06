package com.betacom.fe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table (name = "indirizzi")
public class Indirizzi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idIndirizzo;

    @Column(nullable=false)
    private String via;

    @Column(nullable=false)
    private String citta;

    @Column(nullable=false)
    private String cap;

    private Boolean predefinito;

    @ManyToOne
    @JoinColumn(name="userId")
    private User userId;
}
