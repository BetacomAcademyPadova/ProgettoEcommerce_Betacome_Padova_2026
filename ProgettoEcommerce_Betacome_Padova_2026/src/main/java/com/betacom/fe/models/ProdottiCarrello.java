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
@Table (name = "prodotti_carrello")
public class ProdottiCarrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRiga;

    @ManyToOne
    @JoinColumn(name="carrello")
    private Carrello carrello;

    @ManyToOne
    @JoinColumn(name="prodotto")
    private DivisioneProdotto divisione;

    @Column(nullable=false)
    private Integer quantita;

    @Column(nullable=false)
    private Float prezzo;
}
