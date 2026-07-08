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
@Table (name = "divisione_prodotto")
public class DivisioneProdotto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDivisione;

    @Column(nullable=false)
    private String colore;
    
    @Column(nullable=false)
    private String materiale;

    @Column(nullable=false)
    private Integer altezza;

    @Column(nullable=false)
    private Integer lunghezza;
    
    @Column(nullable=false)
    private Integer larghezza;
    
    @Column(nullable=false, columnDefinition = "int default 0")
    private Integer quantitaDisponibile;

    @ManyToOne
    @JoinColumn(name="idProdotto")
    private Prodotti prodotto;
}
