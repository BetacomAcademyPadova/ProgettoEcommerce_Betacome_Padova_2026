package com.betacom.fe.user;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.betacom.fe.dto.input.AutentiacazioneReq;
import com.betacom.fe.dto.input.ChangePwdReq;
import com.betacom.fe.dto.input.LogInReq;
import com.betacom.fe.dto.input.UserReq;
import com.betacom.fe.dto.output.LoginDTO;
import com.betacom.fe.dto.output.UserDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	

	@Test
	@Order(1)
	public void createTest() throws Exception{
		AutentiacazioneReq usr = new AutentiacazioneReq();
		usr.setNome("User1");
		usr.setCognome("User1");
		usr.setEmail("user1@mail.it");
		usr.setTelefono("+391111111111");
		usr.setUsername("user1");
		usr.setPassword("User_1111");

	    mockMvc.perform(post("/rest/User/create")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(usr)))
	            .andExpect(status().isOk());

	    usr = new AutentiacazioneReq();
	    usr.setNome("User2");
	    usr.setCognome("User2");
	    usr.setEmail("user2@mail.it");
	    usr.setTelefono("+392222222222");
	    usr.setUsername("user2");
	    usr.setPassword("User_2222");

	    mockMvc.perform(post("/rest/User/create")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(usr)))
	            .andExpect(status().isOk());

	    usr = new AutentiacazioneReq();
	    usr.setNome("User3");
	    usr.setCognome("User3");
	    usr.setEmail("user3@mail.it");
	    usr.setTelefono("+393333333333");
	    usr.setUsername("user3");
	    usr.setPassword("User_3333");

	    mockMvc.perform(post("/rest/User/create")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(usr)))
	            .andExpect(status().isOk());
	}
	
	@Test
	@Order(2)
	public void getById() throws Exception{
		MvcResult result = mockMvc.perform(get("/rest/User/getById/"+3))
	            .andExpect(status().isOk())
	            .andReturn();
		  
		String json = result.getResponse().getContentAsString();
		UserDTO usr = objectMapper.readValue(json, new TypeReference<UserDTO>() {});
		log.debug(usr.toString());
	}
	
	@Test
	@Order(3)
	public void updateTest() throws Exception {

	    UserReq req = new UserReq();
	    req.setUserId(1);
	    req.setNome("Mario");
	    req.setCognome("Rossi");
	    req.setTelefono("+391234567890");

	    mockMvc.perform(put("/rest/User/update")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(req)))
	            .andExpect(status().isOk());
	}
	
	@Test
	@Order(4)
	public void loginTest() throws Exception {

	    LogInReq req = new LogInReq();
	    req.setUsername("user1");
	    req.setPassword("User_1111");

	    MvcResult result = mockMvc.perform(post("/rest/User/login")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(req)))
	            .andExpect(status().isOk())
	            .andReturn();

	    String json = result.getResponse().getContentAsString();

	    LoginDTO dto = objectMapper.readValue(json, LoginDTO.class);

	    log.debug(dto.toString());
	}
	
	@Test
	@Order(5)
	public void setRuoloTest() throws Exception {
	    mockMvc.perform(put("/rest/User/setRuolo/user1/admin"))
	            .andExpect(status().isOk());
	    
	    mockMvc.perform(put("/rest/User/setRuolo/user2/Venditore"))
        .andExpect(status().isOk());
	}
	
	@Test
	@Order(6)
	public void getAllTest() throws Exception {

	    MvcResult result = mockMvc.perform(get("/rest/User/getAll"))
	            .andExpect(status().isOk())
	            .andReturn();

	    String json = result.getResponse().getContentAsString();

	    List<UserDTO> users = objectMapper.readValue(json,
	            new TypeReference<List<UserDTO>>() {});

	    assertFalse(users.isEmpty());
	    log.debug(users.toString());
	}
	
	@Test
	@Order(7)
	public void changePwdAndUsernameTest() throws Exception {

	    ChangePwdReq req = new ChangePwdReq();
	    req.setUsername("user3");
	    req.setNewUsername("user33");
	    req.setOldPassword("User_3333");
	    req.setNewPassword("User_1234");

	    mockMvc.perform(put("/rest/User/changePwd")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(req)))
	            .andExpect(status().isOk());

	    mockMvc.perform(put("/rest/User/changeUsername")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(req)))
	            .andExpect(status().isOk());
	    
	    LogInReq login = new LogInReq();
	    login.setUsername("user33");
	    login.setPassword("User_1234");

	    mockMvc.perform(post("/rest/User/login")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(login)))
	            .andExpect(status().isOk());
	}
	
	
}
