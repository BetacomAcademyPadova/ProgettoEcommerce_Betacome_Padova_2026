package com.betacom.fe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table (name = "autenticazione")
public class Autenticazione {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer id;

	 @Column(nullable = false, unique = true)
	 private String username;
	 
	 @Column(nullable = false)
	 private String password;
	 
	 @OneToOne
	 @JoinColumn(name = "user_id")
	 private User user;
}
