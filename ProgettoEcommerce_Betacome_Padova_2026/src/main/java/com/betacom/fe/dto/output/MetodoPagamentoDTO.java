package com.betacom.fe.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MetodoPagamentoDTO {
    private Integer idMetodo;
    private String tipo;      // "card", "satispay" 
    private String dettagli;  // "visa **** 4242"
}