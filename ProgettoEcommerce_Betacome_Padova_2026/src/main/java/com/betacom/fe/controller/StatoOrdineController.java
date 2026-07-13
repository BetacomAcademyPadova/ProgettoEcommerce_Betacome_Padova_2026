package com.betacom.fe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.fe.dto.input.StatoOrdineReq;
import com.betacom.fe.dto.input.StatoPagReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.dto.output.StatoOrdineDTO;
import com.betacom.fe.services.interfaces.IStatoOrdineServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/StatoOrdine")
public class StatoOrdineController {

	private final IStatoOrdineServices statOrdPS;
	
	@PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) StatoOrdineReq req) throws Exception {
		statOrdPS.create(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
    }
	
	@DeleteMapping("delete/{StatoOrdine}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable("StatoOrdine") String stato) throws Exception {
		statOrdPS.delete(stato);
        return ResponseEntity.ok(ResponseDTO.builder().msg("deleted...").build());
    }
	
	@GetMapping("getAll")
    public ResponseEntity<List<StatoOrdineDTO>> getAll() throws Exception {
        return ResponseEntity.ok(statOrdPS.getAll());
    }
	
	@PatchMapping("update/{id}")
	public ResponseEntity<ResponseDTO> update(@RequestBody(required = true) @Validated(ValidationGroups.Update.class) StatoOrdineReq req,
			@PathVariable("id") Integer stato) throws Exception{
		statOrdPS.update(req, stato);
		return ResponseEntity.ok(ResponseDTO.builder().msg("updated...").build());
	}
}
