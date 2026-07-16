package com.betacom.fe.tipo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.betacom.fe.dto.input.ScontoReq;
import com.betacom.fe.dto.output.ScontoDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScontoTest 
{
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @Order(1)
    public void createScontoTest() throws Exception 
    {
        log.debug("create sconto");

        ScontoReq req = new ScontoReq();
        req.setDataInizio("16/07/2026");
        req.setDataFine("18/08/2026");
        req.setValore(20f);
        req.setIdProdotto(2);
        
        mockMvc.perform(post("/rest/Sconto/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
        .andExpect(status().isOk());
    }
    
    @Test
    @Order(2)
    public void createScontoTest2() throws Exception 
    {
        log.debug("create sconto");

        ScontoReq req = new ScontoReq();
        req.setDataInizio("18/09/2026");
        req.setDataFine("18/10/2026");
        req.setValore(25f);
        req.setIdProdotto(2);
        
        mockMvc.perform(post("/rest/Sconto/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
        .andExpect(status().isOk());
    }
    
    @Test
    @Order(3)
    public void getByIdTest() throws Exception {
        log.debug("getById sconto");

        MvcResult result = mockMvc.perform(
                        get("/rest/Sconto/getById/1"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        ScontoDTO dto = objectMapper.readValue(
                json,
                new TypeReference<ScontoDTO>() {}
        );

        log.debug(dto.toString());
    }
    
    @Test
    @Order(4)
    public void getAllTest() throws Exception {
        log.debug("getAll Sconto");

        MvcResult result = mockMvc.perform(
                        get("/rest/Sconto/getAll"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<ScontoDTO> lista = objectMapper.readValue(
                json,
                new TypeReference<List<ScontoDTO>>() {}
        );

        assertFalse(lista.isEmpty());

        log.debug(lista.toString());
    }
    
    @Test
    @Order(5)
    public void updateTest() throws Exception {
        log.debug("update Sconto");

        ScontoReq req = new ScontoReq();
        req.setIdSconto(1);
        req.setValore(30.f);
        req.setDataFine("30/11/2026");

        mockMvc.perform(put("/rest/Sconto/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }
    
    @Test
    @Order(6)
    public void deleteTest() throws Exception {
        log.debug("delete Sconto");

        mockMvc.perform(delete("/rest/Sconto/delete/2"))
                .andExpect(status().isOk());
    }
}
