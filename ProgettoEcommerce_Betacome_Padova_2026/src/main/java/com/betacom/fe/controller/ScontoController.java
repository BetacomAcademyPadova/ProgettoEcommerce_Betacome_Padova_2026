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

import com.betacom.fe.dto.input.ScontoReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.dto.output.ScontoDTO;
import com.betacom.fe.services.interfaces.IScontoServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest/Sconto")
@RequiredArgsConstructor
public class ScontoController 
{
	private final IScontoServices scontoS;
	
	@PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) ScontoReq req) throws Exception 
	{
		scontoS.create(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
	}
	
	@PutMapping("update")
    public ResponseEntity<ResponseDTO> update(@Validated(ValidationGroups.Update.class) ScontoReq req) throws Exception 
	{
		scontoS.update(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("updated...").build());
    }
	
	@DeleteMapping("delete/{idSconto}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable Integer idSconto) throws Exception 
	{
		 scontoS.delete(idSconto);
	     return ResponseEntity.ok(ResponseDTO.builder().msg("deleted...").build());
	}
	
	@GetMapping("getById/{idSconto}")
    public ResponseEntity<ScontoDTO> getById(@PathVariable Integer idSconto) throws Exception 
	{
		return ResponseEntity.ok(scontoS.getById(idSconto));
    }
	
	@GetMapping("getAll")
    public ResponseEntity<List<ScontoDTO>> getAll() throws Exception 
	{
		return ResponseEntity.ok(scontoS.getAll());
    }
}
