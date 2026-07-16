package com.betacom.fe.tipo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.betacom.fe.dto.input.DivisioneProdottoReq;
import lombok.extern.slf4j.Slf4j;
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
        req.setIdProdotto(1);
        
        mockMvc.perform(post("/rest/DivisioneProdotto/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
        .andExpect(status().isOk());
    }
}
