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

import com.betacom.fe.dto.input.ProdottiOrdineReq;
import com.betacom.fe.repositories.IProdottiCarrelloRepository;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdottiOrdineTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private IProdottiCarrelloRepository procarR;
    
    @Test
    @Order(1)
    public void createProdottiOrdineTest() throws Exception {
        log.debug("createProdottiOrdineTest");

        ProdottiOrdineReq req = new ProdottiOrdineReq();
        req.setOrdineId(1);
        req.setProdottoId(2);
        req.setIndirizzoSpedizioneId(3);
        req.setProdottiCarrelloId(1);
        req.setDivisioneOrdineId(1);

        mockMvc.perform(post("/rest/ProdottiOrdine/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

}
