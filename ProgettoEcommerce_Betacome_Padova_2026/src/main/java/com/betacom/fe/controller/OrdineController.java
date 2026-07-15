package com.betacom.fe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.fe.dto.input.OrdineReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.OrdineDTO;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.services.interfaces.IOrdineServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest/Ordine")
@RequiredArgsConstructor
public class OrdineController {
	
	private final IOrdineServices ordS;
	
	@PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) OrdineReq req) throws Exception {
		ordS.create(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
}
	@GetMapping("getById/{idOrdine}")
    public ResponseEntity<OrdineDTO> getById(@PathVariable Integer idOrdine) throws Exception {
    return ResponseEntity.ok(ordS.getById(idOrdine));
    }

	@GetMapping("getAll")
    public ResponseEntity<List<OrdineDTO>> getAll() throws Exception {
    return ResponseEntity.ok(ordS.getAll());
    }
	
	@GetMapping("getAllByUserId/{userId}")
	public ResponseEntity<List<OrdineDTO>> getAllByUserId(@PathVariable Integer userId) throws Exception {
	    return ResponseEntity.ok(ordS.getAllByUserId(userId));
	}

}