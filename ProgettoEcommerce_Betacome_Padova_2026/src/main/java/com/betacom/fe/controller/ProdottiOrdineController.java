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

import com.betacom.fe.dto.input.ProdottiOrdineReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.ProdottiOrdineDTO;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.services.interfaces.IProdottiOrdineServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest/ProdottiOrdine")
@RequiredArgsConstructor
public class ProdottiOrdineController {
	private final IProdottiOrdineServices prodottiOrdineS;
	
	@PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) ProdottiOrdineReq req) throws Exception {
		prodottiOrdineS.create(req);
    	return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
    }
	
	@PutMapping("update")
    public ResponseEntity<ResponseDTO> update(@RequestBody(required = true) @Validated(ValidationGroups.Update.class) ProdottiOrdineReq req) throws Exception {
		prodottiOrdineS.update(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("updated...").build());
    }
	
    @DeleteMapping("delete/{idItem}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Integer idItem) throws Exception {
    	prodottiOrdineS.delete(idItem);
    	return ResponseEntity.ok(ResponseDTO.builder().msg("deleted...").build());    }

    @GetMapping("getById/{idItem}")
    public ResponseEntity<ProdottiOrdineDTO> getById(@PathVariable Integer idItem) throws Exception {
    	return ResponseEntity.ok(prodottiOrdineS.getById(idItem));
    }
    
    @GetMapping("getAll")
    public ResponseEntity<List<ProdottiOrdineDTO>> getAll() throws Exception {
    return ResponseEntity.ok(prodottiOrdineS.getAll());
    }


}
