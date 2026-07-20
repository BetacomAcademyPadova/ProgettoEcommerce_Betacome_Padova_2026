package com.betacom.fe.pagamento;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;

import com.betacom.fe.dto.input.AutentiacazioneReq;
import com.betacom.fe.dto.input.IndirizzoReq;
import com.betacom.fe.dto.input.OrdineReq;
import com.betacom.fe.dto.input.PaymentIntentReq;
import com.betacom.fe.dto.input.RuoloReq;
import com.betacom.fe.dto.input.StatoOrdineReq;
import com.betacom.fe.dto.input.StatoPagReq;
import com.betacom.fe.dto.output.PaymentIntentDTO;
import com.betacom.fe.services.interfaces.IPagamentiServices;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser
public class PagamentiControllerTest {

	@Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private IPagamentiServices pagS;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())   
                .build();
    }

    @Test
    @Order(1)
    public void createIntentTest() throws Exception {
        log.debug("createIntentTest");

        RuoloReq ruoloReq = new RuoloReq();
        ruoloReq.setRuolo("User");

        mockMvc.perform(post("/rest/Ruoli/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ruoloReq)))
                .andExpect(status().isOk());
        
        AutentiacazioneReq userReq = new AutentiacazioneReq();
        userReq.setNome("Test");
        userReq.setCognome("Pagamento");
        userReq.setEmail("testpay@mail.it");
        userReq.setTelefono("+391111111111");
        userReq.setUsername("testpay");
        userReq.setPassword("Test_1111");

        mockMvc.perform(post("/rest/User/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userReq)))
                .andExpect(status().isOk());

        IndirizzoReq indReq = new IndirizzoReq();
        indReq.setIdUser(1);
        indReq.setVia("Via Test 1");
        indReq.setCitta("Padova");
        indReq.setCap("35100");
        indReq.setPredefinito(true);

        mockMvc.perform(post("/rest/Indirizzi/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(indReq)))
                .andExpect(status().isOk());

        StatoOrdineReq statoReq = new StatoOrdineReq();
        statoReq.setStato("In attesa");

        mockMvc.perform(post("/rest/StatoOrdine/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statoReq)))
                .andExpect(status().isOk());

        OrdineReq ordineReq = new OrdineReq();
        ordineReq.setData(LocalDate.now());
        ordineReq.setUserId(1);
        ordineReq.setIndirizzoFatturazioneId(1);
        ordineReq.setStatoId(1);
        ordineReq.setTotale(49.99f);

        mockMvc.perform(post("/rest/Ordine/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordineReq)))
                .andExpect(status().isOk());
        
        StatoPagReq statoPagReq = new StatoPagReq();
        statoPagReq.setStatoPag("In attesa");

        mockMvc.perform(post("/rest/StatoPagamento/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statoPagReq)))
                .andExpect(status().isOk());

        PaymentIntentReq req = new PaymentIntentReq();
        req.setIdOrdine(1);
        req.setSalvaMetodo(true);

        PaymentIntent fakeIntent = Mockito.mock(PaymentIntent.class);
        Mockito.when(fakeIntent.getId()).thenReturn("pi_test_123");
        Mockito.when(fakeIntent.getClientSecret()).thenReturn("pi_test_123_secret");

        try (MockedStatic<PaymentIntent> stripeMock = Mockito.mockStatic(PaymentIntent.class)) {
            stripeMock.when(() -> PaymentIntent.create(Mockito.any(PaymentIntentCreateParams.class)))
                      .thenReturn(fakeIntent);

            MvcResult result = mockMvc.perform(post("/rest/Pagamenti/create-intent")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andReturn();

            String json = result.getResponse().getContentAsString();
            PaymentIntentDTO dto = objectMapper.readValue(json, PaymentIntentDTO.class);
            log.debug(dto.toString());
        }
    }

    @Test
    @Order(2)
    public void createIntentOrdineNonEsisteTest() throws Exception {
        log.debug("createIntentOrdineNonEsisteTest");

        PaymentIntentReq req = new PaymentIntentReq();
        req.setIdOrdine(9999); // doesn't exist

        mockMvc.perform(post("/rest/Pagamenti/create-intent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void getMetodiSalvatiTest() throws Exception {
        log.debug("getMetodiSalvatiTest");

        mockMvc.perform(get("/rest/Pagamenti/metodi-salvati/1"))
                .andExpect(status().isOk());
    }
    
    @Test
    @Order(4)
    public void createIntentGiaCompletatoTest() throws Exception {
        log.debug("createIntentGiaCompletatoTest");

        IndirizzoReq indReq2 = new IndirizzoReq();
        indReq2.setIdUser(1);
        indReq2.setVia("Via Test 2");
        indReq2.setCitta("Padova");
        indReq2.setCap("35100");
        indReq2.setPredefinito(false);

        mockMvc.perform(post("/rest/Indirizzi/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(indReq2)))
                .andExpect(status().isOk());

        OrdineReq ordineReq = new OrdineReq();
        ordineReq.setData(LocalDate.now());
        ordineReq.setUserId(1);
        ordineReq.setIndirizzoFatturazioneId(2);   // <-- changed from 1 to 2
        ordineReq.setStatoId(1);
        ordineReq.setTotale(20.0f);

        mockMvc.perform(post("/rest/Ordine/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordineReq)))
                .andExpect(status().isOk());

        PaymentIntentReq req = new PaymentIntentReq();
        req.setIdOrdine(2);
        req.setSalvaMetodo(false);

        PaymentIntent fakeIntent = Mockito.mock(PaymentIntent.class);
        Mockito.when(fakeIntent.getId()).thenReturn("pi_gia_completato");
        Mockito.when(fakeIntent.getClientSecret()).thenReturn("secret_whatever");

        try (MockedStatic<PaymentIntent> stripeMock = Mockito.mockStatic(PaymentIntent.class)) {
            stripeMock.when(() -> PaymentIntent.create(Mockito.any(PaymentIntentCreateParams.class)))
                      .thenReturn(fakeIntent);

            mockMvc.perform(post("/rest/Pagamenti/create-intent")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk());
        }

        StatoPagReq statoCompletatoReq = new StatoPagReq();
        statoCompletatoReq.setStatoPag("Completato");

        mockMvc.perform(post("/rest/StatoPagamento/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statoCompletatoReq)))
                .andExpect(status().isOk());

        pagS.markSucceeded("pi_gia_completato", null);

        mockMvc.perform(post("/rest/Pagamenti/create-intent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @Order(5)
    public void createIntentMissingIdOrdineTest() throws Exception {
        log.debug("createIntentMissingIdOrdineTest");

        PaymentIntentReq req = new PaymentIntentReq();
        // idOrdine intentionally left null
        req.setSalvaMetodo(true);

        mockMvc.perform(post("/rest/Pagamenti/create-intent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @Order(6)
    public void markSucceededPagamentoNonEsisteTest() throws Exception {
        log.debug("markSucceededPagamentoNonEsisteTest");

        Exception ex = org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
            pagS.markSucceeded("transazione_che_non_esiste", null);
        });

        log.debug("Eccezione attesa: " + ex.getMessage());
    }
    
    @Test
    @Order(7)
    public void markFailedTest() throws Exception {
        log.debug("markFailedTest");

        StatoPagReq statoFallitoReq = new StatoPagReq();
        statoFallitoReq.setStatoPag("Fallito");

        mockMvc.perform(post("/rest/StatoPagamento/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statoFallitoReq)))
                .andExpect(status().isOk());

        pagS.markFailed("pi_test_123", null);
    }
}