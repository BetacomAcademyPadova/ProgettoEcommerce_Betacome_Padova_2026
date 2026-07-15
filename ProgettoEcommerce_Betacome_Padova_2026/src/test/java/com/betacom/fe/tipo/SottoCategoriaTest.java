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

import com.betacom.fe.dto.input.SottoCategoriaReq;
import com.betacom.fe.dto.output.SottoCategoriaDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SottoCategoriaTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	

	@Test
	@Order(1)
	public void createTest() throws Exception{
		log.debug("create");
		List<String> cats = List.of("Metallurgia","Ceramica","Falegname");
		List<String> sotto = List.of("Sotto1","Sotto2","Sotto3");
		cats.forEach(a -> {
			sotto.forEach(b->{
			SottoCategoriaReq req = new SottoCategoriaReq();
			req.setCategoria(a);
			req.setSottoCategoria(b);
			try {
				mockMvc.perform(post("/rest/SottoCategoria/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(req))
						).andExpect(status().isOk());
			} catch (Exception e) {
				log.error("Error in create {}", e.getMessage());
			}
		});});			
	}
	
	@Test
	@Order(4)
	public void listAllTest() throws Exception{
		log.debug("listAllCategorie");
		
		MvcResult result = mockMvc.perform(get("/rest/SottoCategoria/getAll"))
	            .andExpect(status().isOk())
	            .andReturn();
		  
		String json = result.getResponse().getContentAsString();
		
		List<SottoCategoriaDTO> lS = objectMapper.readValue(
	            json,
	            new TypeReference<List<SottoCategoriaDTO>>() {}
	    );
		
		assertFalse(lS.isEmpty());
		
		lS.forEach(s -> log.debug(s.toString()));
		
	}

	@Test
	@Order(3)
	public void deleteTest() throws Exception{
		log.debug("deleteSottoCategoria");
		mockMvc.perform(delete("/rest/SottoCategoria/delete/3"))
	       .andExpect(status().isOk())
	       .andExpect(jsonPath("$.msg").exists());
	}
	
	@Test
	@Order(2)
	public void updateTest() throws Exception{
		SottoCategoriaReq cat = new SottoCategoriaReq();
		cat.setCategoria("Metallurgia");
		cat.setSottoCategoria("Supporti Armadi");
		mockMvc.perform(patch("/rest/SottoCategoria/update/3")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(cat)))
	        .andExpect(status().isOk());
	}

}
