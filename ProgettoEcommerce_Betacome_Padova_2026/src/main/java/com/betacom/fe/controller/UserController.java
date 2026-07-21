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

import com.betacom.fe.dto.input.AutentiacazioneReq;
import com.betacom.fe.dto.input.LogInReq;
import com.betacom.fe.dto.input.UserReq;
import com.betacom.fe.dto.input.ValidationGroups;
import com.betacom.fe.dto.output.LoginDTO;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.dto.output.UserDTO;
import com.betacom.fe.services.interfaces.IUserServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/User")
public class UserController {

	private final IUserServices userS;
	
	@PostMapping("create")
    public ResponseEntity<ResponseDTO> create(@RequestBody(required = true) @Validated(ValidationGroups.Create.class) AutentiacazioneReq req) throws Exception {
		userS.create(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
    }
	
    @PutMapping("update")
    public ResponseEntity<ResponseDTO> update(@RequestBody(required = true) @Validated(ValidationGroups.Update.class) UserReq req) throws Exception {
        userS.update(req);
        return ResponseEntity.ok(ResponseDTO.builder().msg("updated...").build());
    }
    
    @DeleteMapping("delete/{idUser}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Integer idUser) throws Exception {
    	userS.delete(idUser);
        return ResponseEntity.ok(ResponseDTO.builder().msg("deleted...").build());
    }
    
    @GetMapping("getById/{idUser}")
    public ResponseEntity<UserDTO> getByUsername(@PathVariable("idUser") Integer idUser) throws Exception {

        return ResponseEntity.ok(userS.getById(idUser));
    }

    @GetMapping("getAll")
    public ResponseEntity<List<UserDTO>> getAll() throws Exception {
        return ResponseEntity.ok(userS.getAll());
    }
    
    @PostMapping("login")
    public ResponseEntity<LoginDTO> login(@RequestBody @Validated(ValidationGroups.Login.class) LogInReq req) throws Exception {
        return ResponseEntity.ok(userS.login(req));
    }
    
    @PutMapping("setRuolo/{username}/{ruolo}")
    public ResponseEntity<ResponseDTO> update(@PathVariable("username") String usr, @PathVariable("ruolo") String r) throws Exception {
        userS.setRuolo(usr, r);
        return ResponseEntity.ok(ResponseDTO.builder().msg("role set...").build());
    }
}
