package com.betacom.fe.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.ScontoReq;
import com.betacom.fe.dto.output.ScontoDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.ScontoMapper;
import com.betacom.fe.models.Prodotti;
import com.betacom.fe.models.Sconto;
import com.betacom.fe.repositories.IProdottiRepository;
import com.betacom.fe.repositories.IScontoRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IScontoServices;
import com.betacom.fe.utils.Utilities;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScontoImpl implements IScontoServices
{
	private final IScontoRepository repS;
	private final IProdottiRepository repP;
	private final IMessaggioServices msgS;
	
	@Transactional
	@Override
	public void create(ScontoReq req) throws Exception 
	{
		log.debug("Create {}", req);
		
		Prodotti p = repP.findById(req.getIdProdotto())
	            .orElseThrow(() ->
	                new AcademyException(msgS.get("prodotto.no.exists")));
		
		LocalDate dataInizio;
	    LocalDate dataFine;
	    try 
	    {
	        dataInizio = Utilities.stringToDate(req.getDataInizio());
	        dataFine = Utilities.stringToDate(req.getDataFine());
	    } 
	    catch (Exception e) 
	    {
	        throw new AcademyException("Errore nel formato data: " + e.getMessage());
	    }

	    LocalDate oggi = LocalDate.now();
	    if (dataInizio.isBefore(oggi)) 
	        throw new AcademyException("La data di inizio non può essere precedente alla data attuale");
	    if (dataFine.isBefore(dataInizio)) 
	        throw new AcademyException("La data di fine non può essere precedente alla data di inizio");
	    
	    List<Sconto> daEliminare = repS.findSovrapposti(p.getIdProdotto(), dataInizio, dataFine, null);
	    if (!daEliminare.isEmpty()) 
	    {
	    	log.debug("Eliminati {} sconti per il prodotto {}", daEliminare.size(), p.getIdProdotto());
	        repS.deleteAll(daEliminare);
	    }

	    Sconto s = new Sconto();
	    s.setValore(req.getValore());
	    s.setProdotto(p);
	    s.setDataInizio(dataInizio);
	    s.setDataFine(dataFine);
	    
	    repS.save(s);
	}

	@Transactional
	@Override
	public void update(ScontoReq req) throws Exception 
	{
		log.debug("Update {}", req);
		
		Sconto s = repS.findById(req.getIdSconto())
				.orElseThrow(() -> new AcademyException(msgS.get("sconto.no.exists")));

		Optional.ofNullable(req.getValore()).ifPresent(s::setValore);
		
		if (req.getIdProdotto() != null) 
		{
	        Prodotti p = repP.findById(req.getIdProdotto())
	                .orElseThrow(() -> new AcademyException( msgS.get("prodotto.no.exists")));
	        s.setProdotto(p);
	    }

		if (req.getDataInizio() != null || req.getDataFine() != null) 
		{
		    try 
		    {
		        LocalDate dataInizio = (req.getDataInizio() != null) 
		                                ? Utilities.stringToDate(req.getDataInizio()) 
		                                : s.getDataInizio();
		        
		        LocalDate dataFine = (req.getDataFine() != null) 
		                                ? Utilities.stringToDate(req.getDataFine()) 
		                                : s.getDataFine();

		        LocalDate oggi = LocalDate.now();

		        if (dataInizio.isBefore(oggi)) 
		            throw new AcademyException("La data di inizio non può essere precedente alla data attuale");

		        if (dataFine.isBefore(dataInizio)) 
		            throw new AcademyException("La data di fine non può essere precedente alla data di inizio");
		        
		        s.setDataInizio(dataInizio);
		        s.setDataFine(dataFine);
		    } 
		    catch (Exception e) 
		    {
		        throw new AcademyException("Errore nel formato data: " + e.getMessage());
		    }
		}
		
		repS.save(s);
	}

	@Transactional
	@Override
	public void delete(Integer idSconto) throws Exception 
	{
		log.debug("Delete sconto: {}", idSconto);
		
		Sconto s = repS.findById(idSconto)
				.orElseThrow(() -> new AcademyException(msgS.get("sconto.no.exists")));
		repS.delete(s);
	}

	@Transactional
	@Override
	public ScontoDTO getById(Integer idSconto) throws Exception 
	{
		log.debug("Sconto getById: {}", idSconto);
		
		Sconto s = repS.findById(idSconto)
				.orElseThrow(() -> new AcademyException(msgS.get("sconto.no.exists")));

	    return ScontoMapper.buildScontoDTO(s);
	}

	@Transactional
	@Override
	public List<ScontoDTO> getAll() throws Exception 
	{
		log.debug("List");
		
		List<Sconto> lS = repS.findAll();
		
		return ScontoMapper.buildScontoDTOList(lS);
	}

}
