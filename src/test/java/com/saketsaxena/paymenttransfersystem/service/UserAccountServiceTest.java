package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.UserAccount;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @Mock
    private AccountService accountService;
    @InjectMocks
    private UserAccountService userAccountService;

    @Test
    void should_get_user_account_details() {
        when(accountService.getUserAccount(111))
                .thenReturn(new UserAccount(111,"First", "Name", new BigDecimal("100"),
                        "GBP", "abc@abc.com", "street1"));

        assertThat(userAccountService.getUserAccount(111))
                .extracting("accountId", "firstName", "lastName", "balance", "currency", "email", "address")
                .contains(111,"First", "Name", new BigDecimal("100"), "GBP", "abc@abc.com", "street1");
    }

    @Test
    void should_throw_invalid_account_exception_if_account_is_not_present() {
        when(accountService.getUserAccount(100))
                .thenThrow(new InvalidAccountException("Account id 100 is not valid"));

        assertThatExceptionOfType(InvalidAccountException.class)
                .isThrownBy(() -> userAccountService.getUserAccount(100))
                .withMessage("Account id 100 is not valid");
    }
}