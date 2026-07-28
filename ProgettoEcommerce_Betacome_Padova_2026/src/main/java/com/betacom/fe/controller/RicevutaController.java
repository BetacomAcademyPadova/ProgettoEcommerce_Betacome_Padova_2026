package com.betacom.fe.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.fe.dto.input.RicevutaReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.dto.output.RicevutaDTO;
import com.betacom.fe.services.interfaces.IRicevutaServices;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/rest/Ricevuta")
@RequiredArgsConstructor
public class RicevutaController {

    private final IRicevutaServices ricevutaS;


    @PostMapping("create")
    public ResponseEntity<ResponseDTO> create(
            @RequestBody(required = true)
            @Validated(ValidationGroups.Create.class) RicevutaReq req
    ) throws Exception {

        ricevutaS.create(req);

        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .msg("created...")
                        .build()
        );
    }


//    @PutMapping("update")
//    public ResponseEntity<ResponseDTO> update(
//            @RequestBody(required = true)
//            @Validated(ValidationGroups.Update.class) RicevutaReq req
//    ) throws Exception {
//
//        ricevutaS.update(req);
//
//        return ResponseEntity.ok(
//                ResponseDTO.builder()
//                        .msg("updated...")
//                        .build()
//        );
//    }


    @GetMapping("getById/{idRicevuta}")
    public ResponseEntity<RicevutaDTO> getById(
            @PathVariable Integer idRicevuta
    ) throws Exception {

        return ResponseEntity.ok(
                ricevutaS.getById(idRicevuta)
        );
    }


//    @GetMapping("getAll")
//    public ResponseEntity<List<RicevutaDTO>> getAll() throws Exception {
//
//        return ResponseEntity.ok(
//                ricevutaS.getAll()
//        );
//    }


//    @GetMapping("getRicevutaBy/{venditoreId}")
//    public ResponseEntity<List<RicevutaDTO>> getRicevutaBy(
//            @PathVariable Integer venditoreId
//    ) throws Exception {
//
//        return ResponseEntity.ok(
//                ricevutaS.getByVenditore(venditoreId)
//        );
//    }


    @GetMapping("user/{userId}")
    public ResponseEntity<List<RicevutaDTO>> getRicevuteUser(
            @PathVariable Integer userId
    ) throws Exception {

        return ResponseEntity.ok(
                ricevutaS.getByUserId(userId)
        );
    }

    @GetMapping("user/{userId}/date")
    public ResponseEntity<List<RicevutaDTO>> getRicevuteUserByDate(
            @PathVariable Integer userId,
            @RequestParam LocalDate dataInizio,
            @RequestParam LocalDate dataFine
    ) throws Exception {

        return ResponseEntity.ok(
                ricevutaS.getByUserIdAndDateRange(
                        userId,
                        dataInizio,
                        dataFine
                )
        );
    }

    @GetMapping("venditore/{venditoreId}")
    public ResponseEntity<List<RicevutaDTO>> getRicevuteVenditore(
            @PathVariable Integer venditoreId
    ) throws Exception {

        return ResponseEntity.ok(
                ricevutaS.getRicevuteVenditore(venditoreId)
        );
    }


    @GetMapping("venditore/{venditoreId}/date")
    public ResponseEntity<List<RicevutaDTO>> getRicevuteVenditoreByDate(
            @PathVariable Integer venditoreId,
            @RequestParam LocalDate dataInizio,
            @RequestParam LocalDate dataFine
    ) throws Exception {

        return ResponseEntity.ok(
                ricevutaS.getRicevuteVenditoreByDateRange(
                        venditoreId,
                        dataInizio,
                        dataFine
                )
        );
    }
}