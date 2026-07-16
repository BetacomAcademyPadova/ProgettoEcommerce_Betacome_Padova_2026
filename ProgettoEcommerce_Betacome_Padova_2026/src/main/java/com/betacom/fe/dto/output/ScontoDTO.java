package com.betacom.fe.dto.output;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScontoDTO 
{
    private Integer idSconto;
    private Float valore;
    private LocalDate dataInizio;
    private LocalDate dataFine;
}
