package com.betacom.fe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "sotto_categoria", uniqueConstraints = {@UniqueConstraint(columnNames = {"categoria", "sottoCategoria"})})
public class SottoCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSottoCategoria;
    
    @Column(nullable=false)
    private String sottoCategoria;

    @ManyToOne
    @JoinColumn(name="categoria")
    private Categoria categoria;
}
