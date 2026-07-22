package com.betacom.fe.immagini;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.betacom.fe.dto.output.ImmagineDTO;
import com.betacom.fe.dto.output.ProdottoDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void uploadTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("files", "foto.jpg", "image/jpeg", "test".getBytes());
        
        mockMvc.perform(multipart("/rest/Upload/image")
                .file(file)
                .param("id", "3"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void getImagesTest() throws Exception {

        mockMvc.perform(get("/rest/Upload/product")
                .param("id", "1"))
                .andExpect(status().isOk());
    }
    
    @Test
    @Order(3)
    void getUrlTest() throws Exception {

        mockMvc.perform(get("/rest/Upload/getUrl")
                .param("filename", "foto.jpg"))
                .andExpect(status().isOk());
    }
    
    @Test
    @Order(4)
    void uploadMultipleImagesTest() throws Exception {

        MockMultipartFile file1 = new MockMultipartFile("files", "foto.jpg", "image/jpeg", "immagine1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("files", "foto.jpg", "image/jpeg", "immagine2".getBytes());
        MockMultipartFile file3 = new MockMultipartFile("files", "foto.jpg", "image/jpeg", "immagine3".getBytes());

        mockMvc.perform(multipart("/rest/Upload/image")
                .file(file1)
                .file(file2)
                .file(file3)
                .param("id", "3"))
                .andExpect(status().isOk());
        
        MvcResult result = mockMvc.perform(get("/rest/Upload/product")
                .param("id", "3"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<ImmagineDTO> immagini = objectMapper.readValue(json, new TypeReference<List<ImmagineDTO>>() {});
        assertTrue(immagini.size() >= 3);
    }
}
