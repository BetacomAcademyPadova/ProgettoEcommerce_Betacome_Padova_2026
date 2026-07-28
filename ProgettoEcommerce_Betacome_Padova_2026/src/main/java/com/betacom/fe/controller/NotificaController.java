package com.betacom.fe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.fe.dto.output.NotificaDTO;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.services.interfaces.INotificaServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest/Notifica")
@RequiredArgsConstructor
public class NotificaController {

    private final INotificaServices notificaS;

    @GetMapping("/nonLette/{userId}")
    public ResponseEntity<List<NotificaDTO>> getNonLette(
            @PathVariable Integer userId
    ) throws Exception {

        return ResponseEntity.ok(
                notificaS.getNonLette(userId)
        );
    }
    
    @GetMapping("/tutteNonLette")
    public ResponseEntity<List<NotificaDTO>> getTutteNonLette() throws Exception {
        return ResponseEntity.ok(
                notificaS.getTutteNonLette()
        );
    }

    @PutMapping("/segnaLetta/{idNotifica}")
    public ResponseEntity<ResponseDTO> segnaLetta(
            @PathVariable Integer idNotifica
    ) throws Exception {

        notificaS.segnaComeLetta(idNotifica);

        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .msg("Notifica segnata come letta")
                        .build()
        );
    }
    
    @PostMapping("/invia/{userId}")
    public ResponseEntity<ResponseDTO> inviaRichiesta(
            @PathVariable Integer userId,
            @RequestParam String messaggio
    ) throws Exception {
        
        notificaS.inviaRichiesta(userId, messaggio);

        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .msg("Richiesta inviata con successo")
                        .build()
        );
    }
    
    @PutMapping("/accetta/{id}")
    public ResponseEntity<ResponseDTO> accetta(@PathVariable Integer id) throws Exception 
    {
        notificaS.accettaRichiesta(id);
        
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .msg("Richiesta accettata con successo")
                        .build()
        );
    }

    @PutMapping("/rifiuta/{id}")
    public ResponseEntity<ResponseDTO> rifiuta(@PathVariable Integer id) throws Exception 
    {
        notificaS.rifiutaRichiesta(id);
        
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .msg("Richiesta rifiutata con successo")
                        .build()
        );
    }
    
    @GetMapping("/utente/{userId}")
    public ResponseEntity<List<NotificaDTO>> getRichiesteUtente(@PathVariable Integer userId) throws Exception 
    {
        List<NotificaDTO> lista = notificaS.getRichiesteUtente(userId);
        
        return ResponseEntity.ok(lista);
    }
}
