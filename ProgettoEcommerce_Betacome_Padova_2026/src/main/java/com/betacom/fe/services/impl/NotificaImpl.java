package com.betacom.fe.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.output.NotificaDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.NotificaMapper;
import com.betacom.fe.models.DivisioneProdotto;
import com.betacom.fe.models.Notifica;
import com.betacom.fe.models.StatoNotifica;
import com.betacom.fe.models.User;
import com.betacom.fe.repositories.INotificaRepository;
import com.betacom.fe.repositories.IStatoNotificaRepository;
import com.betacom.fe.repositories.IUserRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.INotificaServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificaImpl implements INotificaServices {

    private final INotificaRepository notificaR;
    private final IMessaggioServices msgS;
    private final IUserRepository userR;
    private final IStatoNotificaRepository statoNotificaR;

    @Transactional
    @Override
    public void creaStockAlert(DivisioneProdotto divisione)
            throws Exception {

        User venditore = divisione.getProdotto().getVenditore();

        LocalDateTime dataCreazione = LocalDateTime.now();
        
        StatoNotifica statoInAttesa = statoNotificaR.findByStato("IN ATTESA")
                .orElseThrow(() -> new Exception("Stato 'IN ATTESA' non presente nel database"));

        Notifica notifica = new Notifica();

        notifica.setMessaggio(
                "Stock basso per il prodotto "
                + divisione.getProdotto().getDescrizione()
                + ", colore "
                + divisione.getColore()
                + ". Quantità rimasta: "
                + divisione.getQuantitaDisponibile()
        );

        notifica.setLetta(false);
        notifica.setDataCreazione(dataCreazione);
        notifica.setDataScadenza(dataCreazione.plusMonths(3));
        notifica.setUser(venditore);
        notifica.setDivisioneProdotto(divisione);
        notifica.setStatoNotifica(statoInAttesa);

        notificaR.save(notifica);
    }
    
    @Transactional
    @Override
    public List<NotificaDTO> getNonLette(Integer userId)
            throws Exception {

        return notificaR
                .findByUser_UserIdAndLettaFalse(userId)
                .stream()
                .map(NotificaMapper::toDTO)
                .toList();
    }

    @Transactional
    @Override
    public void segnaComeLetta(Integer idNotifica)
            throws Exception {

        Notifica notifica = notificaR.findById(idNotifica)
                .orElseThrow(() ->
                        new AcademyException(
                                msgS.get("notifica.no.exists")
                        ));

        notifica.setLetta(true);

        notificaR.save(notifica);
    }
    
    @Transactional
	@Override
	public void inviaRichiesta(Integer userId, String messaggio) throws Exception 
	{
    	User utente = userR.findById(userId)
                .orElseThrow(() -> new AcademyException(msgS.get("user.no.exists")));
    	
    	StatoNotifica statoInAttesa = statoNotificaR.findByStato("IN ATTESA")
                .orElseThrow(() -> new Exception("Stato 'IN ATTESA' non presente nel database"));

        LocalDateTime dataCreazione = LocalDateTime.now();

        Notifica notifica = new Notifica();
        notifica.setMessaggio("Richiesta ricevuta dall'utente ID: " + userId + " - " + messaggio);
        notifica.setLetta(false);
        notifica.setDataCreazione(dataCreazione);
        notifica.setDataScadenza(dataCreazione.plusMonths(3));
        notifica.setStatoNotifica(statoInAttesa);
        
        notifica.setUser(utente); 

        notificaR.save(notifica);
	}

    @Transactional
    @Override
    public List<NotificaDTO> getTutteNonLette() throws Exception {
        return notificaR
                .findByLettaFalse()
                .stream()
                .map(NotificaMapper::toDTO)
                .toList();
    }

    @Transactional
	@Override
	public void accettaRichiesta(Integer idNotifica) throws Exception 
	{
    	Notifica notifica = notificaR.findById(idNotifica)
                .orElseThrow(() -> new Exception("Notifica non trovata con ID: " + idNotifica));

        StatoNotifica statoAccettato = statoNotificaR.findByStato("ACCETTATA")
                .orElseThrow(() -> new Exception("Stato 'ACCETTATA' non presente nel database"));

        notifica.setStatoNotifica(statoAccettato);
        notifica.setLetta(true); 
        notificaR.save(notifica);
	}

    @Transactional
	@Override
	public void rifiutaRichiesta(Integer idNotifica) throws Exception 
	{
    	Notifica notifica = notificaR.findById(idNotifica)
                .orElseThrow(() -> new Exception("Notifica non trovata con ID: " + idNotifica));

        StatoNotifica statoRifiutato = statoNotificaR.findByStato("RIFIUTATA")
                .orElseThrow(() -> new Exception("Stato 'RIFIUTATA' non presente nel database"));

        notifica.setStatoNotifica(statoRifiutato);
        notifica.setLetta(true);
        notificaR.save(notifica);
	}
}
