package com.betacom.fe.models;

import java.time.LocalDate;

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
@Table (name = "sconti")
public class Sconto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSconto;

    @ManyToOne
    @JoinColumn(name="prodotto")
    private Prodotti prodotto;

    @Column(nullable=false)
    private Float valore;

    @Column(nullable=false)
    private LocalDate dataInizio;

    @Column(nullable=false)
    private LocalDate dataFine;

}