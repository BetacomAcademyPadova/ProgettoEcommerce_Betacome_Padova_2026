package com.betacom.fe.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.ProdottiOrdineReq;
import com.betacom.fe.dto.output.ProdottiOrdineDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.ProdottiOrdineMapper;
import com.betacom.fe.models.Indirizzi;
import com.betacom.fe.models.Ordini;
import com.betacom.fe.models.Prodotti;
import com.betacom.fe.models.ProdottiCarrello;
import com.betacom.fe.models.ProdottiOrdine;
import com.betacom.fe.repositories.IIndirizziRepository;
import com.betacom.fe.repositories.IOrdineRepository;
import com.betacom.fe.repositories.IProdottiCarrelloRepository;
import com.betacom.fe.repositories.IProdottiOrdineRepository;
import com.betacom.fe.repositories.IProdottiRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IProdottiOrdineServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdottiOrdineImpl implements IProdottiOrdineServices{
	
	private final IProdottiRepository proR;
	private final IProdottiCarrelloRepository procarR;
	private final IProdottiOrdineRepository prordR;
	private final IOrdineRepository ordR;
	private final IIndirizziRepository indR;
	private final IMessaggioServices msgS;

	@Transactional
	@Override
	public void create(ProdottiOrdineReq req) throws Exception {
		Ordini ordine = ordR.findById(req.getOrdineId())
		        .orElseThrow(() ->
		            new AcademyException(
		                msgS.get("ordine.non.esiste")
		            )
		        );
		Prodotti prodotto = proR.findById(req.getProdottoId())
		        .orElseThrow(() ->
		            new AcademyException(
		                msgS.get("prodo.non.esiste")
		            )
		        );
		
		Indirizzi indirizzo = indR.findById(req.getIndirizzoSpedizioneId())
	            .orElseThrow(() ->
	                new AcademyException(
	                    msgS.get("indirizzo.non.esiste")
	                )
	            );
		
		ProdottiCarrello prodottiCar = procarR.findById(req.getProdottiCarrelloId())
	            .orElseThrow(() ->
	                new AcademyException(
	                    msgS.get("prodcar.non.esiste")
	                )
	            );
		
		ProdottiOrdine prodord = new ProdottiOrdine();
		
		prodord.setOrdine(ordine);                 
		prodord.setProdotto(prodotto);             
		prodord.setIndirizzoSpedizione(indirizzo); 

		prodord.setQuantita(prodottiCar.getQuantita());
		prodord.setPrezzo(prodotto.getPrezzo());

		prordR.save(prodord);
		
		
	}

	@Transactional
	@Override
	public void delete(Integer idItem) throws Exception {
		ProdottiOrdine po = prordR.findById(idItem)
	            .orElseThrow(() -> new Exception("Prodotto ordine non trovato"));

		prordR.delete(po);
		
	}

	@Override
	public ProdottiOrdineDTO getById(Integer idItem) throws Exception {
		ProdottiOrdine ord = prordR.findById(idItem)
	            .orElseThrow(() -> new AcademyException(msgS.get("ordine.non.esiste")));

	    return ProdottiOrdineMapper.toDTO(ord);
	}

	@Override
	public List<ProdottiOrdineDTO> getAll() throws Exception {
		return prordR.findAll()
				.stream()
				.map(o -> ProdottiOrdineMapper.toDTO(o))
				.toList();
	}

}
