package com.betacom.fe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.fe.dto.input.StatoPagReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.dto.output.StatoPagDTO;
import com.betacom.fe.services.interfaces.IStatoPagamentoServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/StatoPagamento")
public class StatoPagamentoController {

	private final IStatoPagamentoServices statpPS;
	
	@PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) StatoPagReq req) throws Exception {
		statpPS.create(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
    }
	
	@DeleteMapping("delete/{StatoPagamento}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable("StatoPagamento") String StatoPagamento) throws Exception {
		statpPS.delete(StatoPagamento);
        return ResponseEntity.ok(ResponseDTO.builder().msg("deleted...").build());
    }
	
	@GetMapping("getAll")
    public ResponseEntity<List<StatoPagDTO>> getAll() throws Exception {
        return ResponseEntity.ok(statpPS.getAll());
    }
}
