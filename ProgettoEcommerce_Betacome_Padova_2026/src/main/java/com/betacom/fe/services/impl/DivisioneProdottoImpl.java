package com.betacom.fe.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.DivisioneProdottoReq;
import com.betacom.fe.dto.output.DivisioneProdottoDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.DivisioneProdottoMapper;
import com.betacom.fe.models.DivisioneProdotto;
import com.betacom.fe.models.Prodotti;
import com.betacom.fe.repositories.IDivisioneProdottoRepository;
import com.betacom.fe.repositories.IProdottiRepository;
import com.betacom.fe.services.interfaces.IDivisioneProdottoServices;
import com.betacom.fe.services.interfaces.IMessaggioServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DivisioneProdottoImpl implements IDivisioneProdottoServices
{
	private final IDivisioneProdottoRepository repDivP;
	private final IProdottiRepository repP;
	private final IMessaggioServices msgS;
	
	@Transactional
	@Override
	public void create(DivisioneProdottoReq req) throws Exception 
	{
		log.debug("Create {}", req);
		
		Prodotti p = repP.findById(req.getIdProdotto())
	            .orElseThrow(() ->
	                new AcademyException(msgS.get("prodotto.no.exists")));
		
		DivisioneProdotto divProd = new DivisioneProdotto();
		
		divProd.setColore(req.getColore());
		divProd.setMateriale(req.getMateriale());
		divProd.setAltezza(req.getAltezza());
		divProd.setLunghezza(req.getLunghezza());
		divProd.setLarghezza(req.getLarghezza());
		divProd.setQuantitaDisponibile(req.getQuantitaDisponibile());
		divProd.setProdotto(p);
		
		repDivP.save(divProd);
	}

	@Transactional
	@Override
	public void update(DivisioneProdottoReq req) throws Exception 
	{
		log.debug("Update {}", req);
		
		DivisioneProdotto divProd = repDivP.findById(req.getIdDivisione())
	            .orElseThrow(() -> new AcademyException(msgS.get("divProd.no.exists")));

		Optional.ofNullable(req.getColore()).ifPresent(divProd::setColore);
		Optional.ofNullable(req.getMateriale()).ifPresent(divProd::setMateriale);
		Optional.ofNullable(req.getAltezza()).ifPresent(divProd::setAltezza);
		Optional.ofNullable(req.getLunghezza()).ifPresent(divProd::setLunghezza);
		Optional.ofNullable(req.getLarghezza()).ifPresent(divProd::setLarghezza);
		Optional.ofNullable(req.getQuantitaDisponibile()).ifPresent(divProd::setQuantitaDisponibile);

		if (req.getIdProdotto() != null) 
		{
	        Prodotti p = repP.findById(req.getIdProdotto())
	                .orElseThrow(() -> new AcademyException( msgS.get("prodotto.no.exists")));
	        divProd.setProdotto(p);
	    }
		
		repDivP.save(divProd);
	}

	@Transactional
	@Override
	public void delete(Integer idDivProdotto) throws Exception 
	{
		DivisioneProdotto divProd = repDivP.findById(idDivProdotto)
				.orElseThrow(() -> new AcademyException(msgS.get("divProd.no.exists")));
		repDivP.delete(divProd);
	}

	@Transactional
	@Override
	public DivisioneProdottoDTO getById(Integer idDivProdotto) throws Exception 
	{
		DivisioneProdotto divProd = repDivP.findById(idDivProdotto)
	            .orElseThrow(() -> new AcademyException(msgS.get("divProd.no.exists")));

	    return DivisioneProdottoMapper.buildDivProdDTO(divProd);
	}

	@Transactional
	@Override
	public List<DivisioneProdottoDTO> getAll() throws Exception 
	{
		log.debug("List");
		
		List<DivisioneProdotto> lDivProd = repDivP.findAll();
		
		return DivisioneProdottoMapper.buildDivProdDTOList(lDivProd);
	}

}
