package com.betacom.fe.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Notifica {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer idNotifica;

	    private String messaggio;

	    private Boolean letta;

	    private LocalDateTime dataCreazione;
	    
	    private LocalDateTime dataScadenza;
	    
	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    @ToString.Exclude
	    private User user;

	    @ManyToOne
	    @JoinColumn(name = "divisione_prodotto_id")
	    @ToString.Exclude
	    private DivisioneProdotto divisioneProdotto;

}
