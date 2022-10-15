package com.saketsaxena.paymenttransfersystem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserAccountControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_get_user_account_details() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/{accountId}", 111)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first-name").value("First"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last-name").value("Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value("100.1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("GBP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("abc@abc.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("street1"));
    }
}
