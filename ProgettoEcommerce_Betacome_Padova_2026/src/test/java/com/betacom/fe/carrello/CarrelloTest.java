package com.betacom.fe.carrello;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.betacom.fe.dto.output.CarrelloDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarrelloTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@Order(1)
	public void getByIdCarrello() throws Exception{
		log.debug("getByIdCarrello");
		
		MvcResult result = mockMvc.perform(get("/rest/Carrello/getById/1"))
	            .andExpect(status().isOk())
	            .andReturn();
		  
		String json = result.getResponse().getContentAsString();
		
		CarrelloDTO c = objectMapper.readValue(json,CarrelloDTO.class);
		
		log.debug(c.toString());
	} 
	
	@Test
	@Order(2)
	public void getByIdUser() throws Exception{
		log.debug("getByIdUser");
		
		MvcResult result = mockMvc.perform(get("/rest/Carrello/getByUser/2"))
	            .andExpect(status().isOk())
	            .andReturn();
		  
		String json = result.getResponse().getContentAsString();
		
		CarrelloDTO c = objectMapper.readValue(json,CarrelloDTO.class);
		
		log.debug(c.toString());
	} 

}
