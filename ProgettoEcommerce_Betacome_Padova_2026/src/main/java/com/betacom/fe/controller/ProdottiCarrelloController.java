package com.betacom.fe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.fe.dto.input.ProdottiCarrelloReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.ProdottiCarrelloDTO;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.services.interfaces.IProdottiCarrelloServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest/ProdottiCarrello")
@RequiredArgsConstructor
public class ProdottiCarrelloController {
    private final IProdottiCarrelloServices prodottiCarrelloS;

    @PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@Validated(ValidationGroups.Create.class) ProdottiCarrelloReq req) throws Exception {
    	prodottiCarrelloS.create(req);
    	return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO> update(@Validated(ValidationGroups.Update.class) ProdottiCarrelloReq req) throws Exception {
    	prodottiCarrelloS.update(req);
    	return ResponseEntity.ok(ResponseDTO.builder().msg("updated...").build());
    }

    @DeleteMapping("delete/{idRiga}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Integer idRiga) throws Exception {
    	prodottiCarrelloS.delete(idRiga);
    	return ResponseEntity.ok(ResponseDTO.builder().msg("deleted...").build());    }

    @GetMapping("getById/{idRiga}")
    public ResponseEntity<ProdottiCarrelloDTO> getById(@PathVariable Integer idRiga) throws Exception {
    	return ResponseEntity.ok(prodottiCarrelloS.getById(idRiga));
    }

    @GetMapping("listByCarrello/{idCarrello}")
    public ResponseEntity<List<ProdottiCarrelloDTO>> listByCarrello( @PathVariable Integer idCarrello) throws Exception {
    	return ResponseEntity.ok(prodottiCarrelloS.listByCarrello(idCarrello)
        );
    }
}
