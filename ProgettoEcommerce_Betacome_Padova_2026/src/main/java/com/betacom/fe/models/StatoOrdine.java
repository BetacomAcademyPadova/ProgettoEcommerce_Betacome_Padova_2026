package com.betacom.fe.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name="stato_ordine")
public class StatoOrdine {
	 @Id
	 private String stato;
}
