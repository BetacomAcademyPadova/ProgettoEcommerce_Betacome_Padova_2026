package com.betacom.fe.carrello;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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

import com.betacom.fe.dto.input.CarrelloReq;
import com.betacom.fe.dto.output.CarrelloDTO;
import com.betacom.fe.dto.output.ResponseDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarrelloTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@Order(1)
	public void createCarrelloTest() throws Exception{
		log.debug("create");
		
		CarrelloReq req = new CarrelloReq();
		req.setDataUltimoAgg(LocalDate.now());
		req.setUserId(2);
		try {
			mockMvc.perform(post("/rest/Carrello/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(req))
					).andExpect(status().isOk());
		}catch (Exception e) {
			log.error("Error in create {}", e.getMessage());
		}
			
		req = new CarrelloReq();
		req.setDataUltimoAgg(LocalDate.now());
		req.setUserId(3);
		try {
			mockMvc.perform(post("/rest/Carrello/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(req))
					).andExpect(status().isOk());
		}catch (Exception e) {
			log.error("Error in create {}", e.getMessage());
		}	
	}
	
	@Test
	@Order(2)
	public void createCarrelloTestError() throws Exception{
		log.debug("createCarrelloTestError");
		
		CarrelloReq req = new CarrelloReq();
		req.setDataUltimoAgg(LocalDate.now());
		req.setUserId(2);
		
		MvcResult result = mockMvc.perform(post("/rest/Carrello/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				)
				.andExpect(status().isBadRequest())
				.andReturn();
		
		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("rc create :{}", dto.getMsg());	
	}
	
	@Test
	@Order(3)
	public void deleteCarrelloTest() throws Exception{
		log.debug("deleteCarrelloTest");
		
		CarrelloReq req = new CarrelloReq();
		req.setDataUltimoAgg(LocalDate.now());
		req.setUserId(1);
		
		MvcResult result = mockMvc.perform(post("/rest/Carrello/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				)
				.andExpect(status().isOk())
				.andReturn();
		
		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("rc create :{}", dto.getMsg());	
		
		
		mockMvc.perform(delete("/rest/Carrello/delete/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.msg").exists());
	}
	
	@Test
	@Order(4)
	public void getByIdCarrello() throws Exception{
		log.debug("getByIdCarrello");
		
		MvcResult result = mockMvc.perform(get("/rest/Carrello/getById/1"))
	            .andExpect(status().isOk())
	            .andReturn();
		  
		String json = result.getResponse().getContentAsString();
		
		CarrelloDTO c = objectMapper.readValue(json,CarrelloDTO.class);
		
		log.debug(c.toString());
	} 

}
