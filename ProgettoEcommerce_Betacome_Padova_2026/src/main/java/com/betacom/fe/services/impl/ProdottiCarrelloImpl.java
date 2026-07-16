package com.betacom.fe.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.input.ProdottiCarrelloReq;
import com.betacom.fe.dto.output.ProdottiCarrelloDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.ProdottiCarrelloMapper;
import com.betacom.fe.models.Carrello;
import com.betacom.fe.models.DivisioneProdotto;
import com.betacom.fe.models.Prodotti;
import com.betacom.fe.models.ProdottiCarrello;
import com.betacom.fe.repositories.ICarrelloRepository;
import com.betacom.fe.repositories.IDivisioneProdottoRepository;
import com.betacom.fe.repositories.IProdottiCarrelloRepository;
import com.betacom.fe.repositories.IProdottiRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IProdottiCarrelloServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdottiCarrelloImpl implements IProdottiCarrelloServices {

    private final IProdottiCarrelloRepository repProdCarr;
    private final ICarrelloRepository repCarr;
    private final IDivisioneProdottoRepository repDiv;
    private final IProdottiRepository repProd;
    private final IMessaggioServices msgS;

    @Transactional
    @Override
    public void create(ProdottiCarrelloReq req) throws Exception {
        Carrello carrello = repCarr.findById(req.getIdCarrello())
            .orElseThrow(() -> new AcademyException(msgS.get("carrello.non.esiste")));

        DivisioneProdotto divisione = repDiv.findById(req.getIdDivisioneProdotto())
            .orElseThrow(() -> new AcademyException(msgS.get("divisione.prodotto.non.esiste")));
        
        Prodotti prodotto = repProd.findById(divisione.getProdotto().getIdProdotto())
        		.orElseThrow(() -> new AcademyException(msgS.get("prodotto.non.esiste")));

        ProdottiCarrello rigaEsistente = repProdCarr.findByCarrelloIdCarrelloAndDivisioneIdDivisione(req.getIdCarrello(), req.getIdDivisioneProdotto())
            .orElse(null);

        if (rigaEsistente == null) {
            controllaDisponibilita(divisione,req.getQuantita());

            ProdottiCarrello nuovaRiga = new ProdottiCarrello();
            nuovaRiga.setCarrello(carrello);
            nuovaRiga.setDivisione(divisione);
            nuovaRiga.setQuantita(req.getQuantita());
            nuovaRiga.setPrezzo(prodotto.getPrezzo());

            repProdCarr.save(nuovaRiga);

        } else {
            Integer nuovaQuantita = rigaEsistente.getQuantita() + req.getQuantita();
            controllaDisponibilita(divisione, nuovaQuantita);
            
            rigaEsistente.setQuantita(nuovaQuantita);
            rigaEsistente.setPrezzo(prodotto.getPrezzo());

            repProdCarr.save(rigaEsistente);
        }

        carrello.setDataUltimoAgg(LocalDate.now());
        repCarr.save(carrello);
    }

    @Transactional
    @Override
    public void update(ProdottiCarrelloReq req) throws Exception {
        ProdottiCarrello riga = repProdCarr.findById(req.getIdRiga())
            .orElseThrow(() -> new AcademyException(msgS.get("prodotti.carrello.non.esiste")));
        
        DivisioneProdotto divisione = repDiv.findById(req.getIdDivisioneProdotto())
                .orElseThrow(() -> new AcademyException(msgS.get("divisione.prodotto.non.esiste")));
        
        Prodotti prodotto = repProd.findById(divisione.getProdotto().getIdProdotto())
        		.orElseThrow(() -> new AcademyException(msgS.get("prodotto.non.esiste")));

        controllaDisponibilita(riga.getDivisione(), req.getQuantita());

        riga.setQuantita(req.getQuantita());
        riga.setPrezzo(prodotto.getPrezzo());

        repProdCarr.save(riga);

        Carrello carrello = riga.getCarrello();
        carrello.setDataUltimoAgg(LocalDate.now());

        repCarr.save(carrello);
    }

    @Transactional
    @Override
    public void delete(Integer idRiga) throws Exception {
        ProdottiCarrello riga = repProdCarr.findById(idRiga)
            .orElseThrow(() -> new AcademyException(msgS.get("prodotti.carrello.non.esiste")));

        Carrello carrello = riga.getCarrello();

        repProdCarr.delete(riga);

        carrello.setDataUltimoAgg(LocalDate.now());
        repCarr.save(carrello);
    }

    @Override
    public ProdottiCarrelloDTO getById(Integer idRiga)
            throws Exception {

        ProdottiCarrello riga = repProdCarr.findById(idRiga)
            .orElseThrow(() -> new AcademyException(msgS.get("prodotti.carrello.non.esiste")));

        return ProdottiCarrelloMapper.toDTO(riga);
    }

    @Override
    public List<ProdottiCarrelloDTO> listByCarrello(
            Integer idCarrello) throws Exception {

        if (!repCarr.existsById(idCarrello))
            throw new AcademyException(msgS.get("carrello.non.esiste"));
        
        return repProdCarr.findByCarrelloIdCarrello(idCarrello)
            .stream()
            .map(ProdottiCarrelloMapper::toDTO)
            .toList();
    }


    private void controllaDisponibilita(DivisioneProdotto divisione, Integer quantitaRichiesta) throws AcademyException {

        if (quantitaRichiesta == null || quantitaRichiesta <= 0)
            throw new AcademyException(msgS.get("quantita.non.valida"));

        if (quantitaRichiesta > divisione.getQuantitaDisponibile())
        	throw new AcademyException(msgS.get("prodotto.quantita.insufficiente"));
    }
}