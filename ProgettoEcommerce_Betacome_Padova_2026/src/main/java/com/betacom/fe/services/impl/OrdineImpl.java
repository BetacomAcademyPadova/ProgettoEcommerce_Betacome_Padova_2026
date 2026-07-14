package com.betacom.fe.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.OrdineReq;
import com.betacom.fe.dto.output.OrdineDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.OrdineMapper;
import com.betacom.fe.mapping.ProdottoMapper;
import com.betacom.fe.models.Indirizzi;
import com.betacom.fe.models.Ordini;
import com.betacom.fe.models.Prodotti;
import com.betacom.fe.models.StatoOrdine;
import com.betacom.fe.models.User;
import com.betacom.fe.repositories.IIndirizziRepository;
import com.betacom.fe.repositories.IOrdineRepository;
import com.betacom.fe.repositories.IStatoOrdineRepository;
import com.betacom.fe.repositories.IUserRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IOrdineServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdineImpl implements IOrdineServices {

	private final IUserRepository usR;
	private final IIndirizziRepository indR;
	private final IStatoOrdineRepository staR;
	private final IOrdineRepository ordR;
	private final IMessaggioServices msgS;
	
	
	@Transactional
	@Override
	public void create(OrdineReq req) throws Exception {

	    User user = usR.findById(req.getUserId())
	        .orElseThrow(() ->
	            new AcademyException(
	                msgS.get("user.non.esiste")
	            )
	        );

	    Indirizzi indF = indR.findById(req.getIndirizzoFatturazioneId())
	        .orElseThrow(() ->
	            new AcademyException(
	                msgS.get("indirizzo.non.esiste")
	            )
	        );


	    StatoOrdine stato = staR.findById(req.getStatoId())
	        .orElseThrow(() ->
	            new AcademyException(
	                msgS.get("stato.ordine.non.esiste")
	            )
	        );

	    Ordini ord = new Ordini();

	    ord.setData(req.getData());
	    ord.setTotale(req.getTotale());

	    ord.setUserId(user);
	    ord.setIndirizzoFatturazione(indF);
	    ord.setStato(stato);

	    ordR.save(ord);
	}


	@Override
	public OrdineDTO getById(Integer idOrdine) throws Exception {
		 Ordini ord = ordR.findById(idOrdine)
		            .orElseThrow(() -> new AcademyException(msgS.get("ordine.non.esiste")));

		    return OrdineMapper.toDTO(ord);
		
	}

	@Override
	public List<OrdineDTO> getAll() throws Exception {
		return ordR.findAll()
				.stream()
				.map(o -> OrdineMapper.toDTO(o))
				.toList();
		
	}

}
