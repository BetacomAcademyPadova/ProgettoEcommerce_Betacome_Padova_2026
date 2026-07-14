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
import com.betacom.fe.dto.input.LogInReq;
import com.betacom.fe.dto.input.UserReq;
import com.betacom.fe.dto.output.UserDTO;
import com.betacom.fe.models.User;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
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
		usr.setPassword("user1");

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
	    usr.setPassword("user2");

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
	    usr.setPassword("user3");

	    mockMvc.perform(post("/rest/User/create")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(usr)))
	            .andExpect(status().isOk());
	}
	
	@Test
	@Order(2)
	public void getByIdTest() throws Exception{
		MvcResult result = mockMvc.perform(get("/rest/User/getById/1"))
	            .andExpect(status().isOk())
	            .andReturn();
		  
		String json = result.getResponse().getContentAsString();
		User usr = objectMapper.readValue(json, new TypeReference<User>() {});
		log.debug(usr.toString());
	}
	
	@Test
	@Order(3)
	public void updateTest() throws Exception {

	    UserReq req = new UserReq();
	    req.setUserId(1);
	    req.setNome("Mario");
	    req.setCognome("Rossi");
	    req.setEmail("mario.rossi@mail.it");
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
	    req.setPassword("user1");

	    MvcResult result = mockMvc.perform(post("/rest/User/login")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(req)))
	            .andExpect(status().isOk())
	            .andReturn();

	    String json = result.getResponse().getContentAsString();

	    UserDTO dto = objectMapper.readValue(json, new TypeReference<UserDTO>() {});

	    log.debug(dto.toString());
	}
	
	@Test
	@Order(5)
	public void setRuoloTest() throws Exception {

	    mockMvc.perform(put("/rest/User/setRuolo/1/ADMIN"))
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
}
