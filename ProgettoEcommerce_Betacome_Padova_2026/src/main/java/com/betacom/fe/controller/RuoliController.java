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

import com.betacom.fe.dto.input.RuoloReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.dto.output.RuoliDTO;
import com.betacom.fe.services.interfaces.IRuoliServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/Ruoli")
public class RuoliController {

	private final IRuoliServices ruoliS;
	
	@PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) RuoloReq req) throws Exception {
		ruoliS.create(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
    }
	
	@DeleteMapping("delete/{Ruolo}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable("Ruolo") String idReq) throws Exception {
		ruoliS.delete(idReq);
        return ResponseEntity.ok(ResponseDTO.builder().msg("deleted...").build());
    }
	
	@GetMapping("getAll")
    public ResponseEntity<List<RuoliDTO>> getAll() throws Exception {
        return ResponseEntity.ok(ruoliS.getAll());
    }
	
	@PatchMapping("update/{id}")
	public ResponseEntity<ResponseDTO> update(@RequestBody(required = true) @Validated(ValidationGroups.Update.class) RuoloReq req,
			@PathVariable("id") Integer idRuolo) throws Exception{
		ruoliS.update(req, idRuolo);
		return ResponseEntity.ok(ResponseDTO.builder().msg("updated...").build());
	}
}
