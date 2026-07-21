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

@Entity
@Table(name = "immagini")
@Getter
@Setter
public class Immagini {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_immagine")
    private Integer id;

    @Column(nullable = false)
    private String nomeFile;

    @Column(nullable = false)
    private String percorso;

    private Boolean principale = false;

    @ManyToOne
    @JoinColumn(name = "id_prodotto")
    private Prodotti prodotto;
}
