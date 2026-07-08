package com.betacom.fe.mapping;

import com.betacom.fe.dto.output.SottoCategoriaDTO;
import com.betacom.fe.models.SottoCategoria;

public class SottoCategoriaMapper {
	public static SottoCategoriaDTO toDTO(SottoCategoria r) {
		return SottoCategoriaDTO.builder()
				.sottoCategoria(r.getSottoCategoria())
				.categoria(r.getCategoria().getCategoria())
				.build();
	}
}
