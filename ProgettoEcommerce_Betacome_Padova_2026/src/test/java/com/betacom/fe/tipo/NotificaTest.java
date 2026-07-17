package com.betacom.fe.tipo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.betacom.fe.dto.output.NotificaDTO;
import com.betacom.fe.models.DivisioneProdotto;
import com.betacom.fe.models.Notifica;
import com.betacom.fe.models.User;
import com.betacom.fe.repositories.IDivisioneProdottoRepository;
import com.betacom.fe.repositories.INotificaRepository;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NotificaTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private INotificaRepository notificaR;

    @Autowired
    private IDivisioneProdottoRepository divisioneR;

    private static Integer idNotifica;
    private static Integer userId;

    @Test
    @Order(1)
    public void preparaNotificaTest() throws Exception {

        log.debug("preparaNotificaTest");


        DivisioneProdotto divisione = divisioneR.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Nessuna divisione prodotto presente nel database"));


        User venditore = divisione
                .getProdotto()
                .getVenditore();

        if (venditore == null) {
            throw new RuntimeException(
                    "Il prodotto della divisione non ha un venditore");
        }

        LocalDateTime dataCreazione = LocalDateTime.now();

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

        Notifica notificaSalvata = notificaR.save(notifica);

        idNotifica = notificaSalvata.getIdNotifica();
        userId = venditore.getUserId();

        assertNotNull(idNotifica);
        assertNotNull(userId);
        assertFalse(notificaSalvata.getLetta());
        assertNotNull(notificaSalvata.getMessaggio());
        assertNotNull(notificaSalvata.getDataCreazione());
        assertNotNull(notificaSalvata.getDataScadenza());

        assertEquals(notificaSalvata.getDataCreazione().plusMonths(3),notificaSalvata.getDataScadenza());

        assertEquals(userId,notificaSalvata.getUser().getUserId());

        assertEquals(divisione.getIdDivisione(),notificaSalvata.getDivisioneProdotto().getIdDivisione());

        log.debug("Notifica creata: idNotifica={}, userId={}, divisioneId={}",idNotifica,userId,divisione.getIdDivisione());
    }

    @Test
    @Order(2)
    public void getNonLetteTest() throws Exception {

        log.debug("getNonLetteTest");

        assertNotNull(userId, "userId non valorizzato dal test precedente");

        assertNotNull(idNotifica,"idNotifica non valorizzato dal test precedente");

        MvcResult result = mockMvc.perform(
                        get("/rest/Notifica/nonLette/" + userId))
                .andExpect(status().isOk())
                .andReturn();

        String json = result
                .getResponse()
                .getContentAsString();

        List<NotificaDTO> notifiche = objectMapper.readValue(
                json,
                new TypeReference<List<NotificaDTO>>() {}
        );

        assertNotNull(notifiche);
        assertFalse(notifiche.isEmpty());

        NotificaDTO notificaTrovata = notifiche.stream()
                .filter(notifica ->
                        notifica.getIdNotifica()
                                .equals(idNotifica))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "La notifica creata non è stata restituita dal controller"));

        assertFalse(notificaTrovata.getLetta());
        assertNotNull(notificaTrovata.getMessaggio());
        assertNotNull(notificaTrovata.getDataCreazione());
        assertNotNull(notificaTrovata.getDataScadenza());

        assertEquals(
                notificaTrovata
                        .getDataCreazione()
                        .plusMonths(3),
                notificaTrovata.getDataScadenza()
        );

        log.debug(
                "Notifiche non lette: {}",
                notifiche
        );
    }

    @Test
    @Order(3)
    public void segnaLettaTest() throws Exception {

        log.debug("segnaLettaTest");

        assertNotNull(
                idNotifica,
                "idNotifica non valorizzato dal test precedente"
        );

        mockMvc.perform(put("/rest/Notifica/segnaLetta/" + idNotifica))
                .andExpect(status().isOk());

        Notifica notificaAggiornata = notificaR
                .findById(idNotifica)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Notifica non trovata dopo l'aggiornamento"));

        assertTrue(notificaAggiornata.getLetta());

        log.debug("Notifica {} segnata come letta", idNotifica);
    }

    @Test
    @Order(4)
    public void notificaLettaNonCompareTest() throws Exception {

        log.debug("notificaLettaNonCompareTest");

        assertNotNull(userId,"userId non valorizzato dal test precedente");

        assertNotNull(idNotifica,"idNotifica non valorizzato dal test precedente");

        MvcResult result = mockMvc.perform(get("/rest/Notifica/nonLette/" + userId))
                .andExpect(status().isOk())
                .andReturn();

        String json = result
                .getResponse()
                .getContentAsString();

        List<NotificaDTO> notifiche = objectMapper.readValue(
                json,
                new TypeReference<List<NotificaDTO>>() {}
        );

        assertNotNull(notifiche);

        boolean notificaAncoraPresente = notifiche.stream()
                .anyMatch(notifica ->
                        notifica.getIdNotifica()
                                .equals(idNotifica)
                );

        assertFalse(notificaAncoraPresente);

        log.debug(
                "La notifica letta non compare più tra le notifiche non lette"
        );
    }
    @Test
    @Order(5)
    public void getNonLetteUtenteSenzaNotificheTest() throws Exception {

        Integer userSenzaNotifiche = 1;

        MvcResult result = mockMvc.perform(
                        get("/rest/Notifica/nonLette/" + userSenzaNotifiche)
                )
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<NotificaDTO> notifiche = objectMapper.readValue(
                json,
                new TypeReference<List<NotificaDTO>>() {}
        );

        assertNotNull(notifiche);
    }
    
    @Test
    @Order(6)
    public void segnaLettaNotificaInesistenteTest() throws Exception {

        mockMvc.perform(
                        put("/rest/Notifica/segnaLetta/99999")
                )
                .andExpect(status().isBadRequest());
    }
}
