package com.betacom.fe.tipo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import com.betacom.fe.dto.input.RuoloReq;
import com.betacom.fe.dto.output.RuoliDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RuoliTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@Order(1)
	public void createTest() throws Exception{
		log.debug("create");
		List<String> ruoli = List.of("User","Venditore","Admin","test update", "Test Delete");
		ruoli.forEach(r ->{
			RuoloReq req = new RuoloReq();
			req.setRuolo(r);
			try {
				mockMvc.perform(post("/rest/Ruoli/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(req))
						).andExpect(status().isOk());
			} catch (Exception e) {
				log.error("Error in create {}", e.getMessage());
			}
		});
		RuoloReq req = new RuoloReq();
		req.setRuolo("admin");
		mockMvc.perform(post("/rest/Ruoli/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				).andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(2)
	public void deleteTest() throws Exception{
		log.debug("deleteRuolo");
		
		mockMvc.perform(delete("/rest/Ruoli/delete/" +  "Test Delete"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.msg").exists());
	}
	
	@Test
	@Order(3)
	public void updateTest() throws Exception{
		RuoloReq req = new RuoloReq();
		req.setRuolo("updated");
		mockMvc.perform(patch("/rest/Ruoli/update/"+4)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(req)))
	        .andExpect(status().isOk());
	}
	
	@Test
	@Order(4)
	public void listAllTest() throws Exception{
		log.debug("listAllRuoli");
		
		MvcResult result = mockMvc.perform(get("/rest/Ruoli/getAll"))
	            .andExpect(status().isOk())
	            .andReturn();
		  
		String json = result.getResponse().getContentAsString();
		List<RuoliDTO> lR = objectMapper.readValue(json, new TypeReference<List<RuoliDTO>>() {});
		assertFalse(lR.isEmpty());
		
		lR.forEach(s -> log.debug(s.toString()));
	}
}
