package com.saketsaxena.paymenttransfersystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saketsaxena.paymenttransfersystem.DTOs.UserAccount;
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
public class UserAccountControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_get_user_account_details() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/{accountId}", 111)
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

    @Test
    void should_return_not_found_when_account_does_not_exists() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/{accountId}", 564)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error-message").value("Account id 564 is invalid"));
    }

    @Test
    void should_create_new_user_account() throws Exception {
        UserAccount userAccount = new UserAccount(333,"First", "Name", new BigDecimal("100"),
                "GBP", "abc@abc.com", "street1");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/account/new")
                        .content(new ObjectMapper().writeValueAsString(userAccount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success-message")
                        .value("Account successfully created with id 333"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/{accountId}", 333)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first-name").value("First"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last-name").value("Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value("100"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("GBP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("abc@abc.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("street1"));

    }

    @Test
    void should_return_bad_requset_if_account_is_already_exists() throws Exception{
        UserAccount userAccount = new UserAccount(444,"First", "Name", new BigDecimal("100"),
                "GBP", "abc@abc.com", "street1");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/account/new")
                        .content(new ObjectMapper().writeValueAsString(userAccount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/account/new")
                        .content(new ObjectMapper().writeValueAsString(userAccount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error-message")
                        .value("Account with id 444 already exists"));
    }
}
