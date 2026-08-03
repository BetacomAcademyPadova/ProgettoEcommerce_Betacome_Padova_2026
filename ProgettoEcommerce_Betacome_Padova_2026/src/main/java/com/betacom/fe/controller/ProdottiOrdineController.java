package com.betacom.fe.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<ResponseDTO> create(
			@RequestBody @Validated(ValidationGroups.Create.class) ProdottiOrdineReq req) throws Exception {

		prodottiOrdineS.create(req);

		return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
	}

	@GetMapping("getById/{idItem}")
	public ResponseEntity<ProdottiOrdineDTO> getById(@PathVariable Integer idItem) throws Exception {

		return ResponseEntity.ok(prodottiOrdineS.getById(idItem));
	}

	@GetMapping("getAll")
	public ResponseEntity<List<ProdottiOrdineDTO>> getAll() throws Exception {

		return ResponseEntity.ok(prodottiOrdineS.getAll());
	}

	@GetMapping("cliente/{userId}")
	public ResponseEntity<List<ProdottiOrdineDTO>> getCliente(@PathVariable Integer userId) throws Exception {

		return ResponseEntity.ok(prodottiOrdineS.getByCliente(userId));
	}

	@GetMapping("cliente/{userId}/{inizio}/{fine}")
	public ResponseEntity<List<ProdottiOrdineDTO>> getClienteDate(

			@PathVariable Integer userId,

			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inizio,

			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fine

	) throws Exception {

		return ResponseEntity.ok(prodottiOrdineS.getByCliente(userId, inizio, fine));
	}

	@GetMapping("venditore/{userId}")
	public ResponseEntity<List<ProdottiOrdineDTO>> getVenditore(@PathVariable Integer userId) throws Exception {

		return ResponseEntity.ok(prodottiOrdineS.getByVenditore(userId));
	}

	@GetMapping("venditore/{userId}/{inizio}/{fine}")
	public ResponseEntity<List<ProdottiOrdineDTO>> getVenditoreDate(

			@PathVariable Integer userId,

			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inizio,

			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fine

	) throws Exception {

		return ResponseEntity.ok(prodottiOrdineS.getByVenditore(userId, inizio, fine));
	}
	
	@GetMapping("ordine/{idOrdine}/{userId}")
	public ResponseEntity<List<ProdottiOrdineDTO>> getByOrdine(
	        @PathVariable Integer idOrdine,
	        @PathVariable Integer userId
	) throws Exception {

	    return ResponseEntity.ok(
	            prodottiOrdineS.getByOrdine(idOrdine, userId)
	    );
	}

	@GetMapping("ordine/venditore/{idOrdine}/{userId}")
	public ResponseEntity<List<ProdottiOrdineDTO>> getByOrdineVenditore(
	        @PathVariable Integer idOrdine,
	        @PathVariable Integer userId
	) throws Exception {

	    return ResponseEntity.ok(
	            prodottiOrdineS.getByOrdineVenditore(idOrdine, userId)
	    );
	}

}