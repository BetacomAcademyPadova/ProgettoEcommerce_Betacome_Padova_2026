package com.betacom.fe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
