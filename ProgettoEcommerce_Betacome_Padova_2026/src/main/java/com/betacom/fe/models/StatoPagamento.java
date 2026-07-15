package com.betacom.fe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="stato_pagamenti")
public class StatoPagamento {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStato;

    @Column(unique = true)
	private String stato;
}
