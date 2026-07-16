package com.betacom.fe.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.RicevutaReq;
import com.betacom.fe.dto.output.ProdottiOrdineDTO;
import com.betacom.fe.dto.output.RicevutaDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.ProdottiOrdineMapper;
import com.betacom.fe.mapping.RicevutaMapper;
import com.betacom.fe.models.Ordini;
import com.betacom.fe.models.ProdottiOrdine;
import com.betacom.fe.models.Ricevuta;
import com.betacom.fe.models.User;
import com.betacom.fe.repositories.IOrdineRepository;
import com.betacom.fe.repositories.IProdottiOrdineRepository;
import com.betacom.fe.repositories.IRicevutaRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IRicevutaServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RicevutaImpl implements IRicevutaServices{
	private final IOrdineRepository ordR;
	private final IProdottiOrdineRepository proordR;
	private final IRicevutaRepository ricR;
	private final IMessaggioServices msgS;

	
	@Transactional
	@Override
	public void create(RicevutaReq req) throws Exception {

	    Ordini ordine = ordR.findById(req.getOrdineId())
	            .orElseThrow(() ->
	                    new AcademyException(msgS.get("ordine.non.esiste")));

	    List<ProdottiOrdine> prodottiOrdine = proordR.findByOrdine(ordine);

	    if (prodottiOrdine.isEmpty()) {
	        throw new AcademyException(msgS.get("L'ordine non contiene prodotti"));
	    }

	    Map<User, List<ProdottiOrdine>> prodottiPerVenditore =
	            prodottiOrdine.stream()
	                    .collect(Collectors.groupingBy(
	                            po -> po.getProdotto().getVenditore()));

	    for (Map.Entry<User, List<ProdottiOrdine>> entry : prodottiPerVenditore.entrySet()) {

	        User venditore = entry.getKey();
	        List<ProdottiOrdine> prodottiVenditore = entry.getValue();

	        float imponibile = 0F;

	        for (ProdottiOrdine po : prodottiVenditore) {
	            imponibile += po.getPrezzo() * po.getQuantita();
	        }

	        float iva = imponibile * 0.22F;
	        float totale = imponibile + iva;

	        Ricevuta ricevuta = new Ricevuta();

	        ricevuta.setOrdine(ordine);
	        ricevuta.setVenditore(venditore);
	        ricevuta.setNumeroFattura(generaNumeroFattura());
	        ricevuta.setDataEmissione(LocalDate.now());
	        ricevuta.setImponibile(imponibile);
	        ricevuta.setIva(iva);
	        ricevuta.setTotale(totale);

	        ricR.save(ricevuta);
	    }
	}
	
	@Transactional
	@Override
	public void update(RicevutaReq req) throws Exception {

	    Ricevuta ricevuta = ricR.findById(req.getIdFattura())
	            .orElseThrow(() ->
	                    new AcademyException(msgS.get("Ricevuta non trovata")));

	    List<ProdottiOrdine> prodottiRicevuta = proordR.findByOrdine(ricevuta.getOrdine())
	            .stream()
	            .filter(po -> po.getProdotto()
	                    .getVenditore()
	                    .equals(ricevuta.getVenditore()))
	            .toList();

	    if (prodottiRicevuta.isEmpty()) {
	        throw new AcademyException(
	                msgS.get("Nessun prodotto presente nella ricevuta"));
	    }

	    float imponibile = (float) prodottiRicevuta.stream()
	            .mapToDouble(po -> po.getPrezzo() * po.getQuantita())
	            .sum();

	    float iva = imponibile * 0.22F;
	    float totale = imponibile + iva;

	    ricevuta.setImponibile(imponibile);
	    ricevuta.setIva(iva);
	    ricevuta.setTotale(totale);

	    ricR.save(ricevuta);
	}
	
	@Transactional
	@Override
	public void delete(Integer idRicevuta) throws Exception {
		 Ricevuta ric = ricR.findById(idRicevuta)
		            .orElseThrow(() -> new AcademyException(msgS.get("Elemento non trovato")));

		 ricR.delete(ric);
		
	}
	
	@Override
	public RicevutaDTO getById(Integer idRicevuta) throws Exception {

	    Ricevuta ric = ricR.findById(idRicevuta)
	            .orElseThrow(() ->
	                    new AcademyException(msgS.get("Ricevuta non trovata")));

	    RicevutaDTO dto = RicevutaMapper.toDTO(ric);

	    List<ProdottiOrdineDTO> prodotti = proordR.findByOrdine(ric.getOrdine())
	            .stream()
	            .filter(po -> po.getProdotto()
	                    .getVenditore()
	                    .equals(ric.getVenditore()))
	            .map(po -> {
	                ProdottiOrdineDTO dtoProdotto = new ProdottiOrdineDTO();

	                dtoProdotto.setProdotto(
	                        po.getProdotto().getDescrizione()
	                );

	                dtoProdotto.setQuantita(
	                        po.getQuantita()
	                );

	                dtoProdotto.setPrezzo(
	                        po.getPrezzo()
	                );

	                return dtoProdotto;
	            })
	            .collect(Collectors.toList());

	    dto.setProdotti(prodotti);

	    return dto;
	}
	
	@Override
	public List<RicevutaDTO> getAll() throws Exception {

	    return ricR.findAll()
	            .stream()
	            .map(ricevuta -> {

	                RicevutaDTO dto = RicevutaMapper.toDTO(ricevuta);

	                List<ProdottiOrdineDTO> prodotti = proordR
	                        .findByOrdine(ricevuta.getOrdine())
	                        .stream()
	                        .filter(po -> po.getProdotto()
	                                .getVenditore()
	                                .equals(ricevuta.getVenditore()))
	                        .map(ProdottiOrdineMapper::toDTO)
	                        .toList();

	                dto.setProdotti(prodotti);

	                return dto;

	            })
	            .toList();
	}
	
	private String generaNumeroFattura() {

	    Optional<Ricevuta> ultima = ricR.findTopByOrderByIdFatturaDesc();

	    int progressivo = 1;

	    if (ultima.isPresent() && ultima.get().getNumeroFattura() != null) {

	        String numero = ultima.get()
	                .getNumeroFattura()
	                .replace("FT-", "");

	        progressivo = Integer.parseInt(numero) + 1;
	    }

	    return String.format("FT-%04d", progressivo);
	}

}
