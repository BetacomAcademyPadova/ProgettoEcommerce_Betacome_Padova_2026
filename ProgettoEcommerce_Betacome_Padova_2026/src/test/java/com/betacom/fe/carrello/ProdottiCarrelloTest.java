package com.betacom.fe.carrello;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.betacom.fe.dto.input.ProdottiCarrelloReq;
import com.betacom.fe.dto.output.ProdottiCarrelloDTO;
import com.betacom.fe.dto.output.ResponseDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdottiCarrelloTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void createProdottiCarrelloTest() throws Exception {
        log.debug("createProdottiCarrelloTest");

        ProdottiCarrelloReq req = new ProdottiCarrelloReq();
        req.setIdCarrello(1);
        req.setIdDivisioneProdotto(2);
        req.setQuantita(2);

        mockMvc.perform(post("/rest/ProdottiCarrello/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void createProdottiCarrelloTestError() throws Exception {
        log.debug("createProdottiCarrelloTestError");

        ProdottiCarrelloReq req = new ProdottiCarrelloReq();
        req.setIdCarrello(9999);
        req.setIdDivisioneProdotto(9999);
        req.setQuantita(2);

        MvcResult result = mockMvc.perform(post("/rest/ProdottiCarrello/create")
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
    public void updateProdottiCarrelloTest() throws Exception {
        log.debug("updateProdottiCarrelloTest");

        ProdottiCarrelloReq req = new ProdottiCarrelloReq();
        req.setIdRiga(1);
        req.setIdDivisioneProdotto(2);
        req.setQuantita(3);

        mockMvc.perform(put("/rest/ProdottiCarrello/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").exists());
    }

    @Test
    @Order(4)
    public void getByIdProdottiCarrelloTest() throws Exception {
        log.debug("getByIdProdottiCarrelloTest");

        MvcResult result = mockMvc.perform(get("/rest/ProdottiCarrello/getById/1"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ProdottiCarrelloDTO dto = objectMapper.readValue(json, ProdottiCarrelloDTO.class);

        log.debug(dto.toString());
    }

    @Test
    @Order(5)
    public void listByCarrelloTest() throws Exception {
        log.debug("listByCarrelloTest");

        MvcResult result = mockMvc.perform(get("/rest/ProdottiCarrello/listByCarrello/1"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<?> lista = objectMapper.readValue(json, List.class);

        log.debug("Numero prodotti: {}", lista.size());
    }

    @Test
    @Order(6)
    public void deleteProdottiCarrelloTest() throws Exception {
        log.debug("deleteProdottiCarrelloTest");

        mockMvc.perform(delete("/rest/ProdottiCarrello/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").exists());
    }

}
