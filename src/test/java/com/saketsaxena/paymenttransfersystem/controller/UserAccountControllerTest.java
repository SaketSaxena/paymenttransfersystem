package com.saketsaxena.paymenttransfersystem.controller;

import com.saketsaxena.paymenttransfersystem.DTOs.UserAccount;
import com.saketsaxena.paymenttransfersystem.service.UserAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAccountControllerTest {

    @Mock
    private UserAccountService userAccountService;
    @InjectMocks
    private UserAccountController userAccountController;

    @Test
    void should_get_user_account_details(){
        when(userAccountService.getUserAccount(111))
                .thenReturn(new UserAccount(111,"First", "Name", new BigDecimal("100"),
                        "GBP", "abc@abc.com", "street1"));

        ResponseEntity<?> response = userAccountController.getUserAccount(111);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .extracting("accountId", "firstName", "lastName", "balance", "currency", "email", "address")
                .contains(111,"First", "Name", new BigDecimal("100"), "GBP", "abc@abc.com", "street1");

    }
}