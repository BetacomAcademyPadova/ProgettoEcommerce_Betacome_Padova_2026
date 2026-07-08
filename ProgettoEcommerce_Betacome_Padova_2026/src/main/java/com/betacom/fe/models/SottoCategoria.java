package com.betacom.fe.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name = "sotto_categoria")
public class SottoCategoria {
    @Id
    private String sottoCategoria;

    @ManyToOne
    @JoinColumn(name="categoria")
    private Categoria categoria;
}