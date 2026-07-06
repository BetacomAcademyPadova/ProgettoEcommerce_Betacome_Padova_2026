package com.betacom.fe.utils;

public class Normalizzazione {
	
	public static String norm(String categoria) {
	    return java.util.Arrays.stream(categoria.trim().split("\\s+"))
	            .map(parola -> parola.substring(0, 1).toUpperCase() + parola.substring(1).toLowerCase())
	            .reduce((a, b) -> a + " " + b)
	            .orElse("");
	}
}
