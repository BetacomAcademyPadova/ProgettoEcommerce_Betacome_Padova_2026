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
@Table(name="ordini")
public class Ordini {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrdine;

    @Column(nullable=false)
    private LocalDate data;

    @Column(nullable=false)
    private Float totale;

    @ManyToOne
    @JoinColumn(name="userId", nullable = false)
    private User userId;
    
    @ManyToOne
    @JoinColumn(name="indirizzo_spedizione", nullable = false)
    private Indirizzi indirizzoSpedizione;
    
    @ManyToOne
    @JoinColumn(name="indirizzo_fatturazione", nullable = false)
    private Indirizzi indirizzoFatturazione;
    
    @ManyToOne
    @JoinColumn(name="stato_ordine", nullable = false)
    private StatoOrdine stato;
}