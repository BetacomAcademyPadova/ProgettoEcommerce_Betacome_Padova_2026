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
@Table (name = "prodotti")
public class Prodotti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProdotto;

    @Column(nullable=false)
    private String descrizione;

    @Column(nullable=false)
    private Float prezzo;

    @Column(nullable=false, columnDefinition = "int default 0")
    private Integer quantitaDisponibile;

    @ManyToOne
    @JoinColumn(name="categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name="user")
    private User venditore;

}