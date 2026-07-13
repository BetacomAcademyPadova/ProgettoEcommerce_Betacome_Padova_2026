package com.betacom.fe.dto.output;

import java.time.LocalDate;
import java.util.List;

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
public class CarrelloDTO {

    private Integer idCarrello;
    private LocalDate dataUltimoAgg;
    private UserDTO user;
    private List<ProdottiCarrelloDTO> prodotti;
    private float totale;
}