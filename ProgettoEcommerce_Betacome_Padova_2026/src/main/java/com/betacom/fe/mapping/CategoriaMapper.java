package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.CategoriaDTO;
import com.betacom.fe.models.Categoria;

public class CategoriaMapper {
	public static CategoriaDTO toDTO(Categoria r) {
		return CategoriaDTO.builder()
				.categoria(r.getCategoria())
				.build();
	}
}
