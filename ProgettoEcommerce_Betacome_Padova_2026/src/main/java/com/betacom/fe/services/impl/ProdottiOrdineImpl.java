package com.betacom.fe.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.ProdottiOrdineReq;
import com.betacom.fe.dto.output.ProdottiOrdineDTO;
import com.betacom.fe.dto.output.ProdottoDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.ProdottiOrdineMapper;
import com.betacom.fe.models.DivisioneProdotto;
import com.betacom.fe.models.Indirizzi;
import com.betacom.fe.models.Ordini;
import com.betacom.fe.models.Prodotti;
import com.betacom.fe.models.ProdottiCarrello;
import com.betacom.fe.models.ProdottiOrdine;
import com.betacom.fe.repositories.IIndirizziRepository;
import com.betacom.fe.repositories.IDivisioneProdottoRepository;
import com.betacom.fe.repositories.IOrdineRepository;
import com.betacom.fe.repositories.IProdottiCarrelloRepository;
import com.betacom.fe.repositories.IProdottiOrdineRepository;
import com.betacom.fe.repositories.IProdottiRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.INotificaServices;
import com.betacom.fe.services.interfaces.IProdottiOrdineServices;
import com.betacom.fe.services.interfaces.IProdottiServices;

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
	private final IProdottiServices proS;
	private final IDivisioneProdottoRepository divR;
	private final INotificaServices notificaS;

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
		
		DivisioneProdotto divisione = divR.findById(req.getDivisioneOrdineId())
	            .orElseThrow(() ->
	                    new AcademyException(msgS.get("divisione.non.esiste"))
	            );
		
		Integer quantitaOrdinata = prodottiCar.getQuantita();

	    if (quantitaOrdinata == null || quantitaOrdinata <= 0) {
	        throw new AcademyException(
	                msgS.get("quantita.non.valida")
	        );
	    }

	    if (divisione.getQuantitaDisponibile() < quantitaOrdinata) {
	        throw new AcademyException(
	                msgS.get("quantita.non.disponibile")
	        );
	    }
	    
	    if (!divisione.getProdotto()
	            .getIdProdotto()
	            .equals(prodotto.getIdProdotto())) {

	        throw new AcademyException(
	                msgS.get("divisione.prodotto.non.valida")
	        );
	    }
		
		ProdottiOrdine prodord = new ProdottiOrdine();
		
		prodord.setOrdine(ordine);                 
		prodord.setProdotto(prodotto);             
		prodord.setIndirizzoSpedizione(indirizzo); 
		prodord.setDivisioneOrdine(divisione);
		prodord.setProdottiCarrello(prodottiCar);
		prodord.setQuantita(prodottiCar.getQuantita());
		
		ProdottoDTO pDto = proS.getById(req.getProdottoId());
		prodord.setPrezzo(pDto.getPrezzo());

		prordR.save(prodord);
		
		   int nuovaQuantita =
		            divisione.getQuantitaDisponibile()
		            - quantitaOrdinata;

		    divisione.setQuantitaDisponibile(nuovaQuantita);

		    divR.save(divisione);

		    if (divisione.getStockAlert() != null
		            && nuovaQuantita <= divisione.getStockAlert()) {

		        notificaS.creaStockAlert(divisione);
		    }
	}

	@Transactional
	@Override
	public void delete(Integer idItem) throws Exception {
		ProdottiOrdine po = prordR.findById(idItem)
	            .orElseThrow(() -> new AcademyException(msgS.get("Prodotto ordine non trovato")));

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
