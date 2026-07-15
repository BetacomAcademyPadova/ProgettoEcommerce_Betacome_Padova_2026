package com.betacom.fe.models;

import java.time.LocalDateTime;

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
@Table(name="pagamenti")
public class Pagamenti {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPagamento;

    @OneToOne
    @JoinColumn(name="ordine")
    private Ordini ordine;

    @Column(name = "metodo_pagamento")
    private String metodoPagamento;

    @Column(nullable = false)
    private Boolean salvato = false;

    @ManyToOne
    @JoinColumn(name = "metodo_salvato")
    private MetodoPagamento metodoSalvato;

    @Column(nullable=false)
    private Float importo;

    @Column(nullable=true)
    private LocalDateTime dataPagamento;

    @ManyToOne
    @JoinColumn(name="stato_pagamento")
    private StatoPagamento statoPagamento;

    private String transazioneId;

}
