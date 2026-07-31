package com.betacom.fe.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.betacom.fe.config.JwtService;
import com.betacom.fe.dto.input.AutentiacazioneReq;
import com.betacom.fe.dto.input.ChangePwdReq;
import com.betacom.fe.dto.input.LogInReq;
import com.betacom.fe.dto.input.PwdResetterReq;
import com.betacom.fe.dto.input.PwdTokenReq;
import com.betacom.fe.dto.input.UserReq;
import com.betacom.fe.dto.output.LoginDTO;
import com.betacom.fe.dto.output.UserDTO;
import com.betacom.fe.models.Autenticazione;
import com.betacom.fe.models.Carrello;
import com.betacom.fe.models.ResetToken;
import com.betacom.fe.models.Ruoli;
import com.betacom.fe.models.User;
import com.betacom.fe.repositories.IAutenticazioneRepository;
import com.betacom.fe.repositories.ICarrelloRepository;
import com.betacom.fe.repositories.IProdottiRepository;
import com.betacom.fe.repositories.IPwdResetRepository;
import com.betacom.fe.repositories.IRuoliRepository;
import com.betacom.fe.repositories.IUserRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IUserServices;
import com.betacom.fe.utils.Normalizzazione;
import com.betacom.fe.utils.ResetEmail;

import jakarta.transaction.Transactional;

import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.mapping.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserImpl implements IUserServices{

    @Autowired
    private JwtService jwtService;
	
	@Autowired
	private IPwdResetRepository pwdResetR;
	@Autowired
	private ResetEmail resetEmail;
	private final IUserRepository repUser;
	private final IRuoliRepository ruoloR;
	private final IAutenticazioneRepository repAut;
    private final IMessaggioServices msgS;
    private final PasswordEncoder passwordEncoder;
    private final ICarrelloRepository repCarr;
    private final IProdottiRepository repProdotti;
	
	@Override
	@Transactional
	public void create(AutentiacazioneReq req) throws Exception {
		User ut = new User();
		ut.setNome(req.getNome());
		ut.setCognome(req.getCognome());
		ut.setEmail(req.getEmail());
		repUser.findByEmail(req.getEmail())
	       .ifPresent(u -> {
	           throw new AcademyException(msgS.get("email.present"));
	       });
		ut.setTelefono(req.getTelefono());
		ut.setRuolo(ruoloR.findByRuolo("User").orElseThrow(() -> new AcademyException(msgS.get("role.no.exists"))));
		ut = repUser.save(ut);
		
		Autenticazione aut = new Autenticazione();
		aut.setUsername(req.getUsername());
		repAut.findByUsername(req.getUsername())
	      .ifPresent(u -> {
	          throw new AcademyException(msgS.get("username.present"));
	      });
		aut.setPassword(passwordEncoder.encode(req.getPassword()));
		aut.setUser(ut);
		repAut.save(aut);
		
		Carrello carr = new Carrello();
	    carr.setUserId(ut);
	    carr.setDataUltimoAgg(LocalDate.now());

	    repCarr.save(carr);
	}

	@Override
	@Transactional
	public void update(UserReq req) throws Exception {
		User usr = repUser.findById(req.getUserId())
				.orElseThrow(() -> new AcademyException(msgS.get("user.non.esiste")));
	    Optional.ofNullable(req.getEmail())
        .filter(email -> !email.equals(usr.getEmail()))
        .ifPresent(email -> {
            repUser.findByEmail(email)
                    .ifPresent(u -> {
                        throw new AcademyException(msgS.get("email.present"));
                    });
            usr.setEmail(email);
        });
		Optional.ofNullable(req.getNome()).ifPresent(t -> usr.setNome(t));
		Optional.ofNullable(req.getCognome()).ifPresent(t -> usr.setCognome(t));
		Optional.ofNullable(req.getTelefono()).ifPresent(t -> usr.setTelefono(t));
		repUser.save(usr);		
	}

	@Override
	@Transactional
	public void delete(Integer idUser) throws Exception {
		User usr = repUser.findById(idUser)
				.orElseThrow(() -> new AcademyException(msgS.get("user.non.esiste")));
		
		boolean hasProdotti = repProdotti.existsByVenditore_UserId(idUser);
		if (hasProdotti) 
			throw new AcademyException("Questo venditore ha dei prodotti attivi e non può essere eliminato.");
		
		repUser.delete(usr);
	}

	@Override
	public UserDTO getById(Integer idUser) throws Exception {
		
		Autenticazione aut = repAut.findById(idUser)
                .orElseThrow(() -> new AcademyException(msgS.get("login.error")));
		
		User utente = repUser.findById(repAut.findById(idUser)
		                .orElseThrow(() -> new AcademyException(msgS.get("login.error")))
		                .getUser().getUserId()
					).orElseThrow(() -> new AcademyException(msgS.get("user.non.esiste")));

		 UserDTO dto = new UserDTO();
		 dto.setUserId(utente.getUserId());
		 dto.setNome(utente.getNome());
		 dto.setCognome(utente.getCognome());
		 dto.setEmail(utente.getEmail());
		 dto.setTelefono(utente.getTelefono());
		 dto.setRuolo(utente.getRuolo().getRuolo());
		 dto.setUsername(aut.getUsername());
		 return dto;
	}

	@Override
	public List<UserDTO> getAll() throws Exception {
		return repUser.findAll()
	            .stream()
	            .map(u -> UserMapper.toDTO(u))
	            .toList();
	}

	@Override
	public LoginDTO login(LogInReq req) throws Exception {
	    Autenticazione aut = repAut.findByUsername(req.getUsername())
	            .orElseThrow(() -> new AcademyException(msgS.get("login.error")));
	    if (!passwordEncoder.matches(req.getPassword(), aut.getPassword())) {
	        throw new AcademyException(msgS.get("login.error"));
	    }
	    String token = jwtService.generateToken(aut.getUsername());
	    
	    UserDTO userDto = UserMapper.toDTO(aut.getUser());
	    userDto.setUsername(aut.getUsername());

	    return new LoginDTO(token, userDto);
	}
	
	@Override
	@Transactional
	public void setRuolo(String usr, String ruolo) throws Exception {
		LogInReq req = new LogInReq();
		req.setUsername(usr);
		Autenticazione idUser = repAut.findByUsername(req.getUsername())
	            .orElseThrow(() -> new AcademyException(msgS.get("login.error")));
		User utente = repUser.findById(idUser.getUser().getUserId())
				.orElseThrow(() -> new AcademyException(msgS.get("user.non.esiste")));
		Ruoli r = ruoloR.findByRuolo(Normalizzazione.norm(ruolo))
				.orElseThrow(() -> new AcademyException(msgS.get("role.no.exists")));
		utente.setRuolo(r);
		repUser.save(utente);
	}

	
	@Override
	@Transactional
	public void changePwd(ChangePwdReq req) throws Exception {
	    Autenticazione user = repAut.findByUsername(req.getUsername())
	            .orElseThrow(() -> new AcademyException(msgS.get("user.non.esiste")));

	    if (!passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
	        throw new AcademyException(msgS.get("user.wrong.password"));
	    }

	    user.setPassword(passwordEncoder.encode(req.getNewPassword()));

	    repAut.save(user);
	}
	

	@Override
	@Transactional
	public void changeUsername(ChangePwdReq req) throws Exception {
		Autenticazione user = repAut.findByUsername(req.getUsername())
	            .orElseThrow(() -> new AcademyException(msgS.get("user.non.esiste")));

	    Optional.ofNullable(req.getNewUsername().trim())
	            .filter(newUsername -> !newUsername.equals(user.getUsername()))
	            .ifPresent(newUsername -> {
	                repAut.findByUsername(newUsername)
	                        .ifPresent(u -> {
	                            throw new AcademyException(msgS.get("username.present"));
	                        });
	                user.setUsername(newUsername);
	                repAut.save(user);
	            });
		
	}

	@Override
	@Transactional
	public void forgotPassword(PwdTokenReq req) throws Exception {
	    User user = repUser.findByEmail(req.getEmail())
	            .orElseThrow(() -> new AcademyException("user.notfound"));

	    ResetToken resetToken = pwdResetR.findByUser(user)
	            .map(token -> {
	                token.setTimer(LocalDateTime.now().plusMinutes(15));
	                return token;
	            })
	            .orElseGet(() -> {
	                ResetToken token = new ResetToken();
	                token.setUser(user);
	                token.setTimer(LocalDateTime.now().plusMinutes(15));
	                return token;
	            });

	    resetToken = pwdResetR.save(resetToken);

	    resetEmail.sendResetPasswordMail(
	            user.getEmail(),
	            user.getNome(),
	            resetToken.getId().toString()
	    );
	}
	
	@Override
	@Transactional
	public void resetPassword(PwdResetterReq req) throws Exception {

	    ResetToken resetToken = pwdResetR.findById(UUID.fromString(req.getToken()))
	    		.orElseThrow(() -> new AcademyException("token.invalid"));

	    if(resetToken.getTimer().isBefore(LocalDateTime.now())) {
	        throw new AcademyException("token.expired");
	    }

	    User user = resetToken.getUser();
	    user.getAutenticazione().setPassword(
	        passwordEncoder.encode(req.getPassword())
	    );

	    repUser.save(user);
	    pwdResetR.delete(resetToken);
	}
}
