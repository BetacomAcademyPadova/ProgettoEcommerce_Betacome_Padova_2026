package com.betacom.fe.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.fe.dto.output.NotificaDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.NotificaMapper;
import com.betacom.fe.models.DivisioneProdotto;
import com.betacom.fe.models.Notifica;
import com.betacom.fe.models.User;
import com.betacom.fe.repositories.INotificaRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.INotificaServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificaImpl implements INotificaServices {

    private final INotificaRepository notificaR;
    private final IMessaggioServices msgS;

    @Transactional
    @Override
    public void creaStockAlert(DivisioneProdotto divisione)
            throws Exception {

        User venditore = divisione.getProdotto().getVenditore();

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
        notifica.setDataCreazione(LocalDateTime.now());
        notifica.setUser(venditore);
        notifica.setDivisioneProdotto(divisione);

        notificaR.save(notifica);
    }

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
}
