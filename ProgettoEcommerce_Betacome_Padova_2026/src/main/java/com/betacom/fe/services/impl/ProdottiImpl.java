package com.betacom.fe.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.ProdottoReq;
import com.betacom.fe.dto.output.ProdottoDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.ProdottoMapper;
import com.betacom.fe.models.Prodotti;
import com.betacom.fe.models.SottoCategoria;
import com.betacom.fe.models.User;
import com.betacom.fe.repositories.IProdottiRepository;
import com.betacom.fe.repositories.ISottoCategoriaRepository;
import com.betacom.fe.repositories.IUserRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IProdottiServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProdottiImpl implements IProdottiServices {
	
	private final IProdottiRepository proR;
	private final IMessaggioServices msgS;
	private final ISottoCategoriaRepository sottoCategoriaR;
	private final IUserRepository userR;
	

	@Transactional
	@Override
	public void create(ProdottoReq req) throws Exception {

	    SottoCategoria sottoCategoria = sottoCategoriaR
	            .findById(req.getIdSottoCategoria())
	            .orElseThrow(() ->
	                new AcademyException(
	                    msgS.get("sottocat.non.esiste")
	                )
	            );

	    User usr = userR
	            .findById(req.getIdUser())
	            .orElseThrow(() ->
	                new AcademyException(
	                    msgS.get("venditore.non.esiste")
	                )
	            );

	    Prodotti pro = new Prodotti();

	    pro.setDescrizione(req.getDescrizione());
	    pro.setPrezzo(req.getPrezzo());
	    pro.setQuantitaDisponibile(req.getQuantitaDisponibile());
	    pro.setStockAlert(req.getStockAlert());
	    pro.setSottoCategoria(sottoCategoria);
	    pro.setVenditore(usr);

	    proR.save(pro);
	}

	@Transactional
	@Override
	public void update(ProdottoReq req) throws Exception {

	    Prodotti pro = proR.findById(req.getIdProdotto())
	            .orElseThrow(() -> new AcademyException(msgS.get("prod.non.esiste")));

	    Optional.ofNullable(req.getDescrizione()).ifPresent(pro::setDescrizione);
	    Optional.ofNullable(req.getPrezzo()).ifPresent(pro::setPrezzo);
	    Optional.ofNullable(req.getQuantitaDisponibile()).ifPresent(pro::setQuantitaDisponibile);
	    Optional.ofNullable(req.getStockAlert()).ifPresent(pro::setStockAlert);

	    if (req.getIdSottoCategoria() != null) {
	    	SottoCategoria sottoCategoria = sottoCategoriaR.findById(req.getIdSottoCategoria())
	                .orElseThrow(() -> new AcademyException(msgS.get("sottocat.non.esiste")));
	    	pro.setSottoCategoria(sottoCategoria);
	    }

	    if (req.getIdUser() != null) {
	        User usr = userR.findById(req.getIdUser())
	                .orElseThrow(() -> new AcademyException( msgS.get("venditore.non.esiste")));
	        pro.setVenditore(usr);
	    }

	    proR.save(pro);
	}
	@Transactional
	@Override
	public void delete(Integer idProdotto) throws Exception {
		Prodotti pro = proR.findById(idProdotto).orElseThrow(() -> new AcademyException(msgS.get("prod.non.esiste")));
		proR.delete(pro);
		
	}
	@Transactional
	@Override
	public ProdottoDTO getById(Integer idProdotto) throws Exception {

	    Prodotti prodotto = proR.findById(idProdotto)
	            .orElseThrow(() -> new AcademyException(msgS.get("prod.non.esiste")));

	    return ProdottoMapper.toDTO(prodotto);
	}
	@Transactional
	@Override
	public List<ProdottoDTO> getAll() throws Exception {
		return proR.findAll()
				.stream()
				.map(p -> ProdottoMapper.toDTO(p))
				.toList();
		
	}

}
