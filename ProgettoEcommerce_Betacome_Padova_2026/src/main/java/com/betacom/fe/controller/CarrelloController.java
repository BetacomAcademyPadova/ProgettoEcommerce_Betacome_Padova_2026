package com.betacom.fe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.fe.dto.input.CarrelloReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.CarrelloDTO;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.services.interfaces.ICarrelloServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest/Carrello")
@RequiredArgsConstructor
public class CarrelloController {
    private final ICarrelloServices carrelloS;

    @GetMapping("getById/{idCarrello}")
    public ResponseEntity<CarrelloDTO> getById(@PathVariable Integer idCarrello) throws Exception {
    	return ResponseEntity.ok(carrelloS.getById(idCarrello));
    }
    
    @GetMapping("getByUser/{idUser}")
    public ResponseEntity<CarrelloDTO> getByUser(@PathVariable Integer idUser) throws Exception {
        return ResponseEntity.ok(carrelloS.getByUser(idUser));
    }
}
