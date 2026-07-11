package com.betacom.fe.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name = "carrello")
public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCarrello;

    private LocalDate dataUltimoAgg;

    @OneToOne
    @JoinColumn(name="userId")
    private User userId;
    
    @OneToMany(
    		mappedBy = "carrello",
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
        private List<ProdottiCarrello> prodotti;
}