package com.betacom.fe.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.SottoCategoriaReq;
import com.betacom.fe.dto.output.SottoCategoriaDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.SottoCategoriaMapper;
import com.betacom.fe.models.Categoria;
import com.betacom.fe.models.SottoCategoria;
import com.betacom.fe.repositories.ICategoriaRepository;
import com.betacom.fe.repositories.ISottoCategoriaRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.ISottoCategoriaServices;
import com.betacom.fe.utils.Normalizzazione;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class SottoCategoriaImpl implements ISottoCategoriaServices{
	private final ISottoCategoriaRepository sottoCatR;
	private final ICategoriaRepository catR;
    private final IMessaggioServices msgS;
	
	@Override
    @Transactional
	public void create(SottoCategoriaReq req) throws Exception {
    	String cat = Normalizzazione.norm(req.getCategoria());
	    Categoria categoria = catR.findByCategoria(cat)
	            .orElseThrow(() -> new AcademyException(msgS.get("cat.not.exists")));
	    
	    String sotocat = Normalizzazione.norm(req.getSottoCategoria());
	    sottoCatR.findBySottoCategoriaAndCategoria(sotocat, categoria)
			.ifPresent(st -> { throw new AcademyException(msgS.get("sotcat.exists"));});

	    SottoCategoria sotto = new SottoCategoria();
	    sotto.setSottoCategoria(sotocat);
	    sotto.setCategoria(categoria);

	    sottoCatR.save(sotto);
	}

	@Override
	public List<SottoCategoriaDTO> getAll() throws Exception {
		return sottoCatR.findAll().stream()
                .map(a -> SottoCategoriaMapper.toDTO(a))
                .toList();
	}

	@Override
    @Transactional
	public void delete(Integer id) throws Exception {
		SottoCategoria sotto = sottoCatR.findById(id)
				.orElseThrow(() -> new AcademyException(msgS.get("sottocat.no.exists")));
		sottoCatR.delete(sotto);		
	}

	@Override
	@Transactional
	public void update(SottoCategoriaReq req, Integer id) throws Exception {

	    SottoCategoria sotto = sottoCatR.findById(id)
	            .orElseThrow(() -> new AcademyException(msgS.get("sottocat.no.exists")));

	    Categoria categoria = catR.findByCategoria(Normalizzazione.norm(req.getCategoria()))
	            .orElseThrow(() -> new AcademyException(msgS.get("cat.not.exists")));

	    sotto.setSottoCategoria(Normalizzazione.norm(req.getSottoCategoria()));
	    sotto.setCategoria(categoria);

	    sottoCatR.save(sotto);
	}

}
