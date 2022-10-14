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
public class AccountBalanceControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_get_account_balance_if_account_id_is_valid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/{accountId}/balance", 111)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.account-id").value(111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value("100.1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("GBP"));
    }

    @Test
    void should_get_error_message_if_account_id_is_not_valid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/{accountId}/balance", 100)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("Account id 100 is not valid"));
    }

    @Test
    void should_get_mini_statement_if_account_id_is_valid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/transfer-fund")
                .content(new ObjectMapper().writeValueAsString(new PaymentTransfer(111, 222, new BigDecimal("20"))))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/{accountId}/statements/mini", 111)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].account-id").value(111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].amount").value("20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].currency").value("GBP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].type").value("DEBIT"));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transfer-fund")
                        .content(new ObjectMapper().writeValueAsString(new PaymentTransfer(222, 111, new BigDecimal("20"))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_get_error_message_if_account_id_is_not_valid_while_getting_mini_statement() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/{accountId}/statements/mini", 100)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("Account id 100 is not valid"));
    }
}
