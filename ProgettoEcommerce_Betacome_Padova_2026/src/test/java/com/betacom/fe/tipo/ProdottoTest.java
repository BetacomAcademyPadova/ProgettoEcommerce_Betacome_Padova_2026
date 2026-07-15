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

import com.betacom.fe.dto.input.ProdottoReq;
import com.betacom.fe.dto.output.ProdottoDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdottoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void createTest() throws Exception {
        log.debug("create prodotto");

        ProdottoReq req = new ProdottoReq();
        req.setDescrizione("Tavolo test");
        req.setPrezzo(300.0f);
        req.setQuantitaDisponibile(10);
        req.setStockAlert(2);
        req.setIdSottoCategoria(1);
        req.setIdUser(2);

        mockMvc.perform(post("/rest/Prodotto/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void getByIdTest() throws Exception {
        log.debug("getById prodotto");

        MvcResult result = mockMvc.perform(
                        get("/rest/Prodotto/getById/1"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        ProdottoDTO dto = objectMapper.readValue(
                json,
                new TypeReference<ProdottoDTO>() {}
        );

        log.debug(dto.toString());
    }

    @Test
    @Order(3)
    public void getAllTest() throws Exception {
        log.debug("getAll prodotti");

        MvcResult result = mockMvc.perform(
                        get("/rest/Prodotto/getAll"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<ProdottoDTO> lista = objectMapper.readValue(
                json,
                new TypeReference<List<ProdottoDTO>>() {}
        );

        assertFalse(lista.isEmpty());

        log.debug(lista.toString());
    }

    @Test
    @Order(4)
    public void searchTest() throws Exception {
        log.debug("search prodotti");

        MvcResult result = mockMvc.perform(
                        get("/rest/Prodotto/search")
                                .param("descrizione", "Tavolo test")
                                .param("prezzo", "300.0")
                                .param("sottocategoria", "Sotto1"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<ProdottoDTO> lista = objectMapper.readValue(
                json,
                new TypeReference<List<ProdottoDTO>>() {}
        );

        assertFalse(lista.isEmpty());

        log.debug(lista.toString());
    }

    @Test
    @Order(5)
    public void updateTest() throws Exception {
        log.debug("update prodotto");

        ProdottoReq req = new ProdottoReq();
        req.setIdProdotto(1);
        req.setDescrizione("Tavolo aggiornato");
        req.setPrezzo(350.0f);
        req.setQuantitaDisponibile(15);
        req.setStockAlert(3);

        mockMvc.perform(put("/rest/Prodotto/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }
    
    @Test
    @Order(6)
    public void updateCategoriaVenditoreTest() throws Exception {

        ProdottoReq req = new ProdottoReq();
        req.setIdProdotto(1);
        req.setIdSottoCategoria(2);
        req.setIdUser(2);

        mockMvc.perform(put("/rest/Prodotto/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    public void deleteTest() throws Exception {
        log.debug("delete prodotto");

        mockMvc.perform(delete("/rest/Prodotto/delete/1"))
                .andExpect(status().isOk());
    }
    

}

