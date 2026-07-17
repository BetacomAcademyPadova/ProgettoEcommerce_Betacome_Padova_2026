package com.betacom.fe.tipo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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

import com.betacom.fe.dto.input.OrdineReq;
import com.betacom.fe.dto.output.OrdineDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrdineTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void createOrdineTest() throws Exception {
        log.debug("createOrdineTest");

        OrdineReq req = new OrdineReq();
        req.setData(LocalDate.now());
        req.setIndirizzoFatturazioneId(3);
        req.setStatoId(1);
        req.setTotale(300.0f);
        req.setUserId(2);

        mockMvc.perform(post("/rest/Ordine/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void getByIdTest() throws Exception {
        log.debug("getByIdTest");

        MvcResult result = mockMvc.perform(
                        get("/rest/Ordine/getById/1"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        OrdineDTO dto = objectMapper.readValue(
                json,
                new TypeReference<OrdineDTO>() {}
        );

        log.debug(dto.toString());
    }

    @Test
    @Order(3)
    public void getAllTest() throws Exception {
        log.debug("getAllTest");

        MvcResult result = mockMvc.perform(
                        get("/rest/Ordine/getAll"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<OrdineDTO> list = objectMapper.readValue(
                json,
                new TypeReference<List<OrdineDTO>>() {}
        );

        assertFalse(list.isEmpty());

        log.debug(list.toString());
    }

    @Test
    @Order(4)
    public void getAllByUserIdTest() throws Exception {
        log.debug("getAllByUserIdTest");

        MvcResult result = mockMvc.perform(
                        get("/rest/Ordine/getAllByUserId/2"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<OrdineDTO> list = objectMapper.readValue(
                json,
                new TypeReference<List<OrdineDTO>>() {}
        );

        assertFalse(list.isEmpty());

        log.debug(list.toString());
    }
}
