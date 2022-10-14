package com.saketsaxena.paymenttransfersystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saketsaxena.paymenttransfersystem.DTOs.PaymentTransfer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentTransferControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_transfer_funds_between_account() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transfer-fund")
                        .content(new ObjectMapper().writeValueAsString(new PaymentTransfer(222, 111, new BigDecimal("20"))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/{accountId}/balance", 222)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.account-id").value(222))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value("304.45"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("GBP"));
    }

    @Test
    void should_return_not_found_when_account_id_is_invalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transfer-fund")
                        .content(new ObjectMapper().writeValueAsString(new PaymentTransfer(333, 222, new BigDecimal("20"))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_bad_request_when_sender_account_is_having_insufficient_fund() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transfer-fund")
                        .content(new ObjectMapper().writeValueAsString(new PaymentTransfer(222, 111, new BigDecimal("2000"))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
