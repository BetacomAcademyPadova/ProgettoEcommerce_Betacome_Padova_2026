package com.betacom.fe.tipo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.betacom.fe.dto.input.ProdottiOrdineReq;
import com.betacom.fe.dto.output.ProdottiOrdineDTO;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.repositories.IProdottiCarrelloRepository;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
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
        req.setProdottiCarrelloId(2);
        req.setDivisioneOrdineId(2);

        mockMvc.perform(post("/rest/ProdottiOrdine/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }
    
    @Test
    @Order(2)
    public void createProdottiOrdineTestError() throws Exception {
        log.debug("createProdottiOrdineTestError");

        ProdottiOrdineReq req = new ProdottiOrdineReq();
        req.setOrdineId(2);
        req.setProdottoId(2);
        req.setIndirizzoSpedizioneId(3);
        req.setProdottiCarrelloId(2);
        req.setDivisioneOrdineId(2);

        MvcResult result = mockMvc.perform(post("/rest/ProdottiOrdine/create")
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
    public void createProdottiOrdineTestError2() throws Exception {
        log.debug("createProdottiOrdineTestError2");

        ProdottiOrdineReq req = new ProdottiOrdineReq();
        req.setOrdineId(1);
        req.setProdottoId(1);
        req.setIndirizzoSpedizioneId(3);
        req.setProdottiCarrelloId(2);
        req.setDivisioneOrdineId(2);

        MvcResult result = mockMvc.perform(post("/rest/ProdottiOrdine/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);

        log.debug(dto.getMsg());
    }
    
    @Test
    @Order(4)
    public void createProdottiOrdineTestError3() throws Exception {
        log.debug("createProdottiOrdineTestError3");

        ProdottiOrdineReq req = new ProdottiOrdineReq();
        req.setOrdineId(2);
        req.setProdottoId(1);
        req.setIndirizzoSpedizioneId(1);
        req.setProdottiCarrelloId(2);
        req.setDivisioneOrdineId(2);

        MvcResult result = mockMvc.perform(post("/rest/ProdottiOrdine/create")
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
    public void createProdottiOrdineTestError4() throws Exception {
        log.debug("createProdottiOrdineTestError4");

        ProdottiOrdineReq req = new ProdottiOrdineReq();
        req.setOrdineId(2);
        req.setProdottoId(1);
        req.setIndirizzoSpedizioneId(3);
        req.setProdottiCarrelloId(1);
        req.setDivisioneOrdineId(2);

        MvcResult result = mockMvc.perform(post("/rest/ProdottiOrdine/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);

        log.debug(dto.getMsg());
    }
    
    @Test
    @Order(6)
    public void createProdottiOrdineTestError5() throws Exception {
        log.debug("createProdottiOrdineTestError5");

        ProdottiOrdineReq req = new ProdottiOrdineReq();
        req.setOrdineId(2);
        req.setProdottoId(1);
        req.setIndirizzoSpedizioneId(3);
        req.setProdottiCarrelloId(2);
        req.setDivisioneOrdineId(1);

        MvcResult result = mockMvc.perform(post("/rest/ProdottiOrdine/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);

        log.debug(dto.getMsg());
    }
    
    @Test
    @Order(7)
    public void getByIdProdottiOrdineTest() throws Exception {
        log.debug("getByIdProdottiOrdineTest");

        MvcResult result = mockMvc.perform(get("/rest/ProdottiOrdine/getById/1"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ProdottiOrdineDTO dto = objectMapper.readValue(json, ProdottiOrdineDTO.class);

        log.debug(dto.toString());
    }
    
    @Test
    @Order(8)
    public void getAllTest() throws Exception {
        log.debug("getAll ProdottiOrdine");

        MvcResult result = mockMvc.perform(
                        get("/rest/ProdottiOrdine/getAll"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<ProdottiOrdineDTO> lista = objectMapper.readValue(
                json,
                new TypeReference<List<ProdottiOrdineDTO>>() {}
        );

        assertFalse(lista.isEmpty());

        log.debug(lista.toString());
    }
    
    @Test
    @Order(9)
    public void deleteTest() throws Exception {
        log.debug("delete ProdottiOrdine");

        mockMvc.perform(delete("/rest/ProdottiOrdine/delete/1"))
                .andExpect(status().isOk());
    } 
    
    @Test
    @Order(10)
    public void createProdottiOrdineTest2() throws Exception {
        log.debug("createProdottiOrdineTest2");

        ProdottiOrdineReq req = new ProdottiOrdineReq();

        req.setOrdineId(1);
        req.setProdottoId(2); 
        req.setIndirizzoSpedizioneId(3);
        req.setProdottiCarrelloId(2);
        req.setDivisioneOrdineId(2);

        MvcResult result = mockMvc.perform(post("/rest/ProdottiOrdine/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andReturn();

        log.debug("Status create prodotti ordine: {}",
                result.getResponse().getStatus());

        log.debug("Response: {}",
                result.getResponse().getContentAsString());

        org.junit.jupiter.api.Assertions.assertEquals(
                200,
                result.getResponse().getStatus()
        );
    }

}
