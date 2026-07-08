package com.betacom.fe.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    

    @Column(nullable=false, columnDefinition = "int default 0")
    private Integer stockAlert;

    @ManyToOne
    @JoinColumn(name="sottocategoria")
    private SottoCategoria sottoCategoria;
    
    @OneToMany(mappedBy = "prodotto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DivisioneProdotto> divisioni;
    
    @ManyToOne
    @JoinColumn(name="userId")
    private User venditore;

}