package com.betacom.fe.controller;

import java.util.List;

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

import com.betacom.fe.dto.input.DivisioneProdottoReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.DivisioneProdottoDTO;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.services.interfaces.IDivisioneProdottoServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest/DivisioneProdotto")
@RequiredArgsConstructor
public class DivisioneProdottoController 
{
	private final IDivisioneProdottoServices divProdS;
	
	@PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) DivisioneProdottoReq req) throws Exception 
	{
		divProdS.create(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
	}
	
	@PutMapping("update")
    public ResponseEntity<ResponseDTO> update(@RequestBody @Validated(ValidationGroups.Update.class) DivisioneProdottoReq req) throws Exception 
	{
		divProdS.update(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("updated...").build());
    }
	
	@DeleteMapping("delete/{idDivProdotto}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable Integer idDivProdotto) throws Exception 
	{
		 divProdS.delete(idDivProdotto);
	     return ResponseEntity.ok(ResponseDTO.builder().msg("deleted...").build());
	}
	
	@GetMapping("getById/{idDivProdotto}")
    public ResponseEntity<DivisioneProdottoDTO> getById(@PathVariable Integer idDivProdotto) throws Exception 
	{
		return ResponseEntity.ok(divProdS.getById(idDivProdotto));
    }
	
	@GetMapping("getAll")
    public ResponseEntity<List<DivisioneProdottoDTO>> getAll() throws Exception 
	{
		return ResponseEntity.ok(divProdS.getAll());
    }
}
