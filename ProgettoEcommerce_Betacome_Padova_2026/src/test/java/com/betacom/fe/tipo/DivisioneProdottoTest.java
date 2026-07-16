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

import com.betacom.fe.dto.input.DivisioneProdottoReq;
import com.betacom.fe.dto.output.DivisioneProdottoDTO;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DivisioneProdottoTest 
{
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @Order(1)
    public void createDivProdottoTest() throws Exception 
    {
        log.debug("create divisioneProdotto");

        DivisioneProdottoReq req = new DivisioneProdottoReq();
        req.setColore("Marrone");
        req.setMateriale("Legno");
        req.setAltezza(100);
        req.setLarghezza(100);
        req.setLunghezza(200);
        req.setQuantitaDisponibile(10);
        req.setIdProdotto(2);
        
        mockMvc.perform(post("/rest/DivisioneProdotto/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
        .andExpect(status().isOk());
    }
    
    @Test
    @Order(2)
    public void createDivProdottoTest2() throws Exception 
    {
        log.debug("create divisioneProdotto");

        DivisioneProdottoReq req = new DivisioneProdottoReq();
        req.setColore("Nero");
        req.setMateriale("Legno scuro");
        req.setAltezza(80);
        req.setLarghezza(150);
        req.setLunghezza(150);
        req.setQuantitaDisponibile(20);
        req.setIdProdotto(2);
        
        mockMvc.perform(post("/rest/DivisioneProdotto/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
        .andExpect(status().isOk());
    }
    
    @Test
    @Order(3)
    public void getByIdTest() throws Exception {
        log.debug("getById divisioneProdotto");

        MvcResult result = mockMvc.perform(
                        get("/rest/DivisioneProdotto/getById/1"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        DivisioneProdottoDTO dto = objectMapper.readValue(
                json,
                new TypeReference<DivisioneProdottoDTO>() {}
        );

        log.debug(dto.toString());
    }
    
    @Test
    @Order(4)
    public void getAllTest() throws Exception {
        log.debug("getAll divisioneProdotto");

        MvcResult result = mockMvc.perform(
                        get("/rest/DivisioneProdotto/getAll"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<DivisioneProdottoDTO> lista = objectMapper.readValue(
                json,
                new TypeReference<List<DivisioneProdottoDTO>>() {}
        );

        assertFalse(lista.isEmpty());

        log.debug(lista.toString());
    }
    
    @Test
    @Order(5)
    public void updateTest() throws Exception {
        log.debug("update divisioneProdotto");

        DivisioneProdottoReq req = new DivisioneProdottoReq();
        req.setIdDivisione(2);
        req.setColore("Bianco");
        req.setMateriale("Legno chiaro");

        mockMvc.perform(put("/rest/DivisioneProdotto/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }
    
    @Test
    @Order(6)
    public void deleteTest() throws Exception {
        log.debug("delete divisioneProdotto");

        mockMvc.perform(delete("/rest/DivisioneProdotto/delete/1"))
                .andExpect(status().isOk());
    }
}
