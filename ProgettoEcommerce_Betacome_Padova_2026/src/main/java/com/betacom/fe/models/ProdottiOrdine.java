package com.betacom.fe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="prodotti_ordine")
public class ProdottiOrdine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;

    @ManyToOne
    @JoinColumn(name="ordine")
    private Ordini ordine;

    @ManyToOne
    @JoinColumn(name="prodotto")
    private Prodotti prodotto;

    @Column(nullable=false)
    private Integer quantita;

    @Column(nullable=false)
    private Float prezzo;
    
    @OneToOne
    @JoinColumn(name="indirizzo_spedizione")
    private Indirizzi indirizzoSpedizione;
}
