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

import com.betacom.fe.dto.input.CategoriaReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.CategoriaDTO;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.services.interfaces.ICategoriaServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/Categoria")
public class CategoriaControllo {
	
	private final ICategoriaServices catS;
	
	@PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) CategoriaReq req) throws Exception {
		catS.create(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
    }
	
	@DeleteMapping("delete/{Categoria}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable("Categoria") String Categoria) throws Exception {
		catS.delete(Categoria);
        return ResponseEntity.ok(ResponseDTO.builder().msg("deleted...").build());
    }
	
	@GetMapping("getAll")
    public ResponseEntity<List<CategoriaDTO>> getAll() throws Exception {
        return ResponseEntity.ok(catS.getAll());
    }
}
