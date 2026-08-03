package com.betacom.fe.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.OrdineReq;
import com.betacom.fe.dto.output.OrdineDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.OrdineMapper;
import com.betacom.fe.models.Carrello;
import com.betacom.fe.models.DivisioneProdotto;
import com.betacom.fe.models.Indirizzi;
import com.betacom.fe.models.Ordini;
import com.betacom.fe.models.ProdottiCarrello;
import com.betacom.fe.models.ProdottiOrdine;
import com.betacom.fe.models.StatoOrdine;
import com.betacom.fe.models.User;
import com.betacom.fe.repositories.ICarrelloRepository;
import com.betacom.fe.repositories.IDivisioneProdottoRepository;
import com.betacom.fe.repositories.IIndirizziRepository;
import com.betacom.fe.repositories.IOrdineRepository;
import com.betacom.fe.repositories.IPagamentiRepository;
import com.betacom.fe.repositories.IProdottiCarrelloRepository;
import com.betacom.fe.repositories.IProdottiOrdineRepository;
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
	private final ICarrelloRepository carR;
	private final IProdottiCarrelloRepository proCarR;
	private final IProdottiOrdineRepository proOrdR;
	private final IDivisioneProdottoRepository divR;
	private final IPagamentiRepository pagR;
	
	
	@Transactional
	@Override
	public Integer create(OrdineReq req) throws Exception {

	    User user = usR.findById(req.getUserId())
	        .orElseThrow(() -> new AcademyException(msgS.get("user.non.esiste")));

	    Indirizzi indF = indR.findById(req.getIndirizzoFatturazioneId())
	        .orElseThrow(() -> new AcademyException(msgS.get("indirizzo.non.esiste")));

	    Indirizzi indS = indR.findById(req.getIndirizzoSpedizioneId())
	        .orElseThrow(() -> new AcademyException(msgS.get("indirizzo.non.esiste")));

	    StatoOrdine stato = staR.findById(req.getStatoId())
	        .orElseThrow(() -> new AcademyException(msgS.get("stato.ordine.non.esiste")));

	    Carrello carrello = carR.findByUserId_UserId(req.getUserId())
	        .orElseThrow(() -> new AcademyException(msgS.get("carrello.non.esiste")));

	    List<ProdottiCarrello> righe =
	            proCarR.findByCarrelloIdCarrello(carrello.getIdCarrello());

	    if (righe.isEmpty())
	        throw new AcademyException(msgS.get("carrello.vuoto"));

	    // riusa l'ordine già in attesa di pagamento invece di crearne uno
	    //    nuovo ogni volta che si torna sulla pagina di conferma
	    Ordini ord = ordR
	        .findFirstByUserId_UserIdAndStato_IdStatoOrderByIdOrdineDesc(
	                user.getUserId(), stato.getIdStato())
	        // ma non toccare un ordine il cui pagamento è già andato a buon fine
	        // (es. webhook ancora non arrivato): in quel caso serve un ordine nuovo
	        .filter(o -> pagR.findByOrdineIdOrdine(o.getIdOrdine())
	                .map(p -> p.getStatoPagamento() == null
	                       || !"Completato".equals(p.getStatoPagamento().getStato()))
	                .orElse(true))
	        .orElseGet(Ordini::new);

	    if (ord.getIdOrdine() != null) {
	        // le righe precedenti tornano in giacenza e vengono rimosse:
	        // l'ordine viene ricostruito dal carrello attuale
	        List<ProdottiOrdine> vecchie = proOrdR.findByOrdine(ord);
	        for (ProdottiOrdine po : vecchie) {
	            DivisioneProdotto d = po.getDivisioneOrdine();
	            d.setQuantitaDisponibile(d.getQuantitaDisponibile() + po.getQuantita());
	            divR.save(d);
	        }
	        proOrdR.deleteAll(vecchie);
	        proOrdR.flush();
	    }

	    // testata dell'ordine, totale provvisorio
	    ord.setData(req.getData() != null ? req.getData() : LocalDate.now());
	    ord.setUserId(user);
	    ord.setIndirizzoFatturazione(indF);
	    ord.setIndirizzoSpedizione(indS);
	    ord.setStato(stato);
	    ord.setTotale(0F);

	    Ordini salvato = ordR.save(ord);

	    float totale = 0F;

	    // una riga prodotti_ordine per ogni riga del carrello
	    for (ProdottiCarrello riga : righe) {

	        DivisioneProdotto div = riga.getDivisione();

	        if (riga.getQuantita() == null || riga.getQuantita() <= 0)
	            throw new AcademyException(msgS.get("quantita.non.valida"));

	        if (riga.getQuantita() > div.getQuantitaDisponibile())
	            throw new AcademyException(msgS.get("prodotto.quantita.insufficiente"));

	        ProdottiOrdine po = new ProdottiOrdine();
	        po.setOrdine(salvato);
	        po.setDivisioneOrdine(div);
	        po.setProdotto(div.getProdotto());
	        po.setQuantita(riga.getQuantita());
	        po.setPrezzo(riga.getPrezzo());      
	        po.setIndirizzoSpedizione(indS);
	        proOrdR.save(po);

	        // scala la giacenza
	        div.setQuantitaDisponibile(div.getQuantitaDisponibile() - riga.getQuantita());
	        divR.save(div);

	        totale += riga.getPrezzo() * riga.getQuantita();
	    }

	    // totale calcolato dal server
	    salvato.setTotale(totale);
	    ordR.save(salvato);

	    return salvato.getIdOrdine();
	}
	
	@Transactional
	@Override
	public OrdineDTO getById(Integer idOrdine) throws Exception {
		 Ordini ord = ordR.findById(idOrdine)
		            .orElseThrow(() -> new AcademyException(msgS.get("ordine.non.esiste")));

		    return OrdineMapper.toDTO(ord);
		
	}
	@Transactional
	@Override
	public List<OrdineDTO> getAll() throws Exception {
		return ordR.findAll()
				.stream()
				.map(o -> OrdineMapper.toDTO(o))
				.toList();
		
	}

	@Transactional
	@Override
	public List<OrdineDTO> getAllByUserId(Integer userId) throws Exception {

	    return ordR.findByUserId_UserIdOrderByDataDesc(userId)
	            .stream()
	            .map(o -> OrdineMapper.toDTO(o))
	            .toList();
	}

	@Override
	public List<OrdineDTO> getAllByVenditore(Integer userId) throws Exception {

	    return ordR.findOrdiniVenditore(userId)
	            .stream()
	            .map(OrdineMapper::toDTO)
	            .toList();
	}

}