package com.betacom.fe.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.CategoriaReq;
import com.betacom.fe.dto.output.CategoriaDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.CategoriaMapper;
import com.betacom.fe.models.Categoria;
import com.betacom.fe.repositories.ICategoriaRepository;
import com.betacom.fe.services.interfaces.ICategoriaServices;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.utils.Normalizzazione;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaImpl implements ICategoriaServices{

	private final ICategoriaRepository catRep;
    private final IMessaggioServices msgS;
	
    @Override
    @Transactional
	public void create(CategoriaReq req) throws Exception {
    	String categoria = Normalizzazione.norm(req.getCategoria());
    	catRep.findById(categoria)
        .ifPresent(cat -> {
            throw new RuntimeException(msgS.get("role.exists"));
        });
		
		Categoria cat = new Categoria();
		cat.setCategoria(categoria);
		catRep.save(cat);		
	}
    
    @Transactional
	@Override
	public List<CategoriaDTO> getAll() throws Exception {
		return catRep.findAll().stream()
                .map(a -> CategoriaMapper.toDTO(a))
                .toList();
	}
	@Override
	public void delete(String idCat) throws Exception {
		Categoria cat = catRep.findById(idCat)
				.orElseThrow(() -> new AcademyException(msgS.get("cat.no.exists")));
		catRep.delete(cat);		
	}
}
