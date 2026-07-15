package com.betacom.fe.user;

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

import com.betacom.fe.dto.input.IndirizzoReq;
import com.betacom.fe.dto.output.IndirizzoDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IndirizzoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void createTest() throws Exception {

        IndirizzoReq ind = new IndirizzoReq();
        ind.setVia("Via Roma");
        ind.setCap("00100");
        ind.setCitta("Roma");
        ind.setIdUser(1);

        mockMvc.perform(post("/rest/Indirizzi/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ind)))
                .andExpect(status().isOk());

        ind = new IndirizzoReq();
        ind.setVia("Via Milano");
        ind.setCap("20100");
        ind.setCitta("Milano");
        ind.setIdUser(1);

        mockMvc.perform(post("/rest/Indirizzi/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ind)))
                .andExpect(status().isOk());

        ind = new IndirizzoReq();
        ind.setVia("Via Napoli");
        ind.setCap("80100");
        ind.setCitta("Napoli");
        ind.setIdUser(2);

        mockMvc.perform(post("/rest/Indirizzi/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ind)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void getByIdTest() throws Exception {

        MvcResult result = mockMvc.perform(get("/rest/Indirizzi/getById/1"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        IndirizzoDTO dto = objectMapper.readValue(json,
                new TypeReference<IndirizzoDTO>() {});

        log.debug(dto.toString());
    }

    @Test
    @Order(3)
    public void getAllByUserTest() throws Exception {

        MvcResult result = mockMvc.perform(get("/rest/Indirizzi/getAllByUser/1"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<IndirizzoDTO> list = objectMapper.readValue(
        		json,
                new TypeReference<List<IndirizzoDTO>>() {}
        	);

        assertFalse(list.isEmpty());

        log.debug(list.toString());
    }

    @Test
    @Order(4)
    public void updateTest() throws Exception {

        IndirizzoReq req = new IndirizzoReq();
        req.setIdIndirizzo(1);
        req.setVia("Via Milano");
        req.setCitta("Milano");
        req.setCap("20100");
        req.setIdUser(1);

        mockMvc.perform(put("/rest/Indirizzi/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/rest/Indirizzi/delete/1"))
                .andExpect(status().isOk());
    }
    
    @Test
    @Order(3)
    public void getAllTest() throws Exception {

        MvcResult result = mockMvc.perform(get("/rest/Indirizzi/getAll"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<IndirizzoDTO> list = objectMapper.readValue(json,
                new TypeReference<List<IndirizzoDTO>>() {});

        assertFalse(list.isEmpty());

        log.debug(list.toString());
    }
}