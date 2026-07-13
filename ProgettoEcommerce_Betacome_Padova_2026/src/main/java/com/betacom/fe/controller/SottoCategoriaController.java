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

import com.betacom.fe.dto.input.SottoCategoriaReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.dto.output.SottoCategoriaDTO;
import com.betacom.fe.services.interfaces.ISottoCategoriaServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/SottoCategoria")
public class SottoCategoriaController {

	private final ISottoCategoriaServices sotcatS;
	
	@PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) SottoCategoriaReq req) throws Exception {
		sotcatS.create(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
    }
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable Integer id) throws Exception {
		sotcatS.delete(id);
	    return ResponseEntity.ok(ResponseDTO.builder().msg("deleted...").build());
	}
	
	@GetMapping("getAll")
    public ResponseEntity<List<SottoCategoriaDTO>> getAll() throws Exception {
        return ResponseEntity.ok(sotcatS.getAll());
    }
	
	@PatchMapping("update/{id}")
	public ResponseEntity<ResponseDTO> update(@RequestBody(required = true) @Validated(ValidationGroups.Update.class) SottoCategoriaReq req,
			@PathVariable("id") Integer sotoCat) throws Exception{
		sotcatS.update(req, sotoCat);
		return ResponseEntity.ok(ResponseDTO.builder().msg("updated...").build());
	}
}
