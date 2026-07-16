package com.betacom.fe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) CarrelloReq req) throws Exception {
    	carrelloS.create(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO> update(@RequestBody(required = true) @Validated(ValidationGroups.Update.class) CarrelloReq req) throws Exception {
    	carrelloS.update(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("updated...").build());
    }

    @DeleteMapping("delete/{idCarrello}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Integer idCarrello) throws Exception {
    	carrelloS.delete(idCarrello);
        return ResponseEntity.ok(ResponseDTO.builder().msg("deleted...").build());
    }

    @GetMapping("getById/{idCarrello}")
    public ResponseEntity<CarrelloDTO> getById(@PathVariable Integer idCarrello) throws Exception {
    	return ResponseEntity.ok(carrelloS.getById(idCarrello));
    }
}
