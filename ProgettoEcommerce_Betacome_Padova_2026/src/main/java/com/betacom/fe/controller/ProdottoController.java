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

import com.betacom.fe.dto.input.ProdottoReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.ProdottoDTO;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.services.interfaces.IProdottiServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest/Prodotto")
@RequiredArgsConstructor
public class ProdottoController {
	
	private final IProdottiServices proS;
	
	@PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) ProdottoReq req) throws Exception {
		proS.create(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
}
	
	@PutMapping("update")
    public ResponseEntity<ResponseDTO> update(@Validated(ValidationGroups.Update.class) ProdottoReq req) throws Exception {
		proS.update(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("updated...").build());
    }
	
	@DeleteMapping("delete/{idProdotto}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable Integer idProdotto) throws Exception {
		 proS.delete(idProdotto);
	     return ResponseEntity.ok(ResponseDTO.builder().msg("deleted...").build());
	    }
	  
	@GetMapping("getById/{idProdotto}")
	    public ResponseEntity<ProdottoDTO> getById(@PathVariable Integer idProdotto) throws Exception {
	    return ResponseEntity.ok(proS.getById(idProdotto));
	    }
	
	@GetMapping("getAll")
	    public ResponseEntity<List<ProdottoDTO>> getAll() throws Exception {
	    return ResponseEntity.ok(proS.getAll());
	    }

	
}
