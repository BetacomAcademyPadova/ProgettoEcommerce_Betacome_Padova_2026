package com.betacom.fe.tipo;

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

import com.betacom.fe.dto.input.RicevutaReq;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.dto.output.RicevutaDTO;
import com.betacom.fe.repositories.IProdottiRepository;
import com.betacom.fe.repositories.IRicevutaRepository;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

import tools.jackson.core.type.TypeReference;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RicevutaTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private IRicevutaRepository ricR;
    
    @Autowired
    private IProdottiRepository proR;    
    
    @Test
    @Order(1)
    public void createRicevutaTest() throws Exception {
        log.debug("createRicevutaTest");

        RicevutaReq req = new RicevutaReq();
        req.setOrdineId(1);


        mockMvc.perform(post("/rest/Ricevuta/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }
    
    @Test
    @Order(2)
    public void createRicevutaErrorTest() throws Exception {
        log.debug("createRicevutaErrorTest");

        RicevutaReq req = new RicevutaReq();
        req.setOrdineId(9999);

        MvcResult result = mockMvc.perform(post("/rest/Ricevuta/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);

        log.debug(dto.getMsg());
    }
    
    @Test
    @Order(3)
    public void updateRicevutaTest() throws Exception {
        log.debug("updateRicevutaTest");

        RicevutaReq req = new RicevutaReq();
        req.setIdFattura(1);

        mockMvc.perform(put("/rest/Ricevuta/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void updateRicevutaErrorTest() throws Exception {
        log.debug("updateRicevutaErrorTest");

        RicevutaReq req = new RicevutaReq();
        req.setIdFattura(9999);

        MvcResult result = mockMvc.perform(put("/rest/Ricevuta/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);

        log.debug(dto.getMsg());
    }

    @Test
    @Order(5)
    public void getByIdRicevutaTest() throws Exception {
        log.debug("getByIdRicevutaTest");

        MvcResult result = mockMvc.perform(get("/rest/Ricevuta/getById/1"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        RicevutaDTO dto = objectMapper.readValue(json, RicevutaDTO.class);

        log.debug(dto.toString());
    }

    @Test
    @Order(6)
    public void getAllTest() throws Exception {
        log.debug("getAll Ricevuta");

        MvcResult result = mockMvc.perform(
                        get("/rest/Ricevuta/getAll"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<RicevutaDTO> lista = objectMapper.readValue(
                json,
                new TypeReference<List<RicevutaDTO>>() {}
        );

        assertFalse(lista.isEmpty());

        log.debug(lista.toString());
    }
    
    @Test
    @Order(7)
    public void getAllByVenditore() throws Exception {
        log.debug("getAllbyVenditore");

        MvcResult result = mockMvc.perform(
                        get("/rest/Ricevuta/getRicevutaBy/"+2))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<RicevutaDTO> lista = objectMapper.readValue(
                json,
                new TypeReference<List<RicevutaDTO>>() {}
        );

        assertFalse(lista.isEmpty());

        log.debug(lista.toString());
    }
    
}