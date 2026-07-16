package com.betacom.fe.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.DivisioneProdottoReq;
import com.betacom.fe.dto.input.ProdottoReq;
import com.betacom.fe.dto.input.SottoCategoriaReq;
import com.betacom.fe.dto.output.ProdottoDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.ProdottoMapper;
import com.betacom.fe.models.Prodotti;
import com.betacom.fe.models.Sconto;
import com.betacom.fe.models.SottoCategoria;
import com.betacom.fe.models.User;
import com.betacom.fe.repositories.IProdottiRepository;
import com.betacom.fe.repositories.IScontoRepository;
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
	private final IScontoRepository scontoR;
	
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
	public ProdottoDTO getById(Integer idProdotto) throws Exception 
	{

	    Prodotti prodotto = proR.findById(idProdotto)
	            .orElseThrow(() -> new AcademyException(msgS.get("prod.non.esiste")));
	    
	    return getProdottoConPrezzoCalcolato(prodotto);
	}
	
	@Transactional
	@Override
	public List<ProdottoDTO> getAll() throws Exception 
	{
		return proR.findAll()
	            .stream()
	            .map(this::getProdottoConPrezzoCalcolato)
	            .toList();
	}

	@Transactional
	@Override
	public List<ProdottoDTO> search(
			ProdottoReq pReq, 
			DivisioneProdottoReq req,
			SottoCategoriaReq sReq,
			Boolean sconti
			) throws Exception 
	{
		
		List<Prodotti> lista;
		if (sconti != null && sconti) 
		{
	        lista = proR.findByFiltriESconti(
	                pReq.getDescrizione(),
	                pReq.getPrezzo(),
	                req.getColore(), 
	                sReq.getSottoCategoria(),
	                req.getMateriale(), 
	                req.getAltezza(),
	                req.getLunghezza(),
	                req.getLarghezza()
	        );
	    } 
		else 
		{
	        lista = proR.findByFiltri(
	                pReq.getDescrizione(),
	                pReq.getPrezzo(),
	                req.getColore(), 
	                sReq.getSottoCategoria(),
	                req.getMateriale(), 
	                req.getAltezza(),
	                req.getLunghezza(),
	                req.getLarghezza()
	        );
	    }
	    
		if (lista == null || lista.isEmpty()) 
	        throw new AcademyException(msgS.get("prodotti.ntfnd"));
		
		return lista.stream()
	            .map(p -> {
	                Sconto s = scontoR.findByIdProdotto(p.getIdProdotto());
	                ProdottoDTO dto = ProdottoMapper.buildListByParams(p, req, s);
	                
	                LocalDate oggi = LocalDate.now();
	                if (s != null && !oggi.isBefore(s.getDataInizio()) && !oggi.isAfter(s.getDataFine())) 
	                {
	                    float prezzoScontato = p.getPrezzo() * (1 - (s.getValore() / 100.0f));
	                    dto.setPrezzo(prezzoScontato);
	                }
	                return dto;
	            })
	            .toList();
	}

	@Transactional
	private ProdottoDTO getProdottoConPrezzoCalcolato(Prodotti p) 
	{
		LocalDate oggi = LocalDate.now();

	    Sconto s = scontoR.findAll().stream()
	            .filter(sc -> sc.getProdotto().getIdProdotto().equals(p.getIdProdotto()))
	            .filter(sc -> !oggi.isBefore(sc.getDataInizio()) && !oggi.isAfter(sc.getDataFine()))
	            .findFirst()
	            .orElse(null);

	    ProdottoDTO dto = ProdottoMapper.toDTO(p, s);

	    if (s != null) 
	    {
	        float percentuale = s.getValore() / 100.0f;
	        float riduzione = p.getPrezzo() * percentuale;
	        float prezzoScontato = p.getPrezzo() - riduzione;
	        
	        dto.setPrezzo(prezzoScontato);
	    }
	    
	    return dto;
	}
}
