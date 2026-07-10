package com.betacom.fe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.fe.dto.input.IndirizzoReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.services.interfaces.IIndirizzoServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/Indirizzi")
public class IndirizziController {
	private final IIndirizzoServices indS;

	@PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) IndirizzoReq req) throws Exception {
		indS.create(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
    }
	
    @PutMapping("update")
    public ResponseEntity<ResponseDTO> update(@RequestBody(required = true) @Validated(ValidationGroups.Update.class) IndirizzoReq req) throws Exception {
    	indS.update(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("updated...").build());
    }
    
    @DeleteMapping("delete/{idIndirizzo}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Integer idIndirizzo) throws Exception {
    	indS.delete(idIndirizzo);
        return ResponseEntity.ok(ResponseDTO.builder().msg("deleted...").build());
    }
    
}
