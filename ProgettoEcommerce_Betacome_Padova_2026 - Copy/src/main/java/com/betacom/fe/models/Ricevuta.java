package com.betacom.fe.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="ricevuta")
public class Ricevuta {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFattura;

    @OneToOne
    @JoinColumn(name="ordine")
    private Ordini ordine;

    @Column(nullable=false)
    private String numeroFattura;

    @Column(nullable=false)
    private LocalDate dataEmissione;

    @Column(nullable=false)
    private Float imponibile;

    @Column(nullable=false)
    private Float iva;

    @Column(nullable=false)
    private Float totale;
}
