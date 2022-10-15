package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.UserAccount;
import com.saketsaxena.paymenttransfersystem.exception.AccountAlreadyExists;
import com.saketsaxena.paymenttransfersystem.exception.BadRequestException;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
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

    @Test
    void should_create_new_user_account(){
        UserAccount userAccount = new UserAccount(333,"First", "Name", new BigDecimal("100"),
                "GBP", "abc@abc.com", "street1");
        userAccountService.createUserAccount(userAccount);

        verify(accountService).createUserAccount(userAccount);
    }

    @Test
    void should_not_create_user_account_if_same_account_id_is_already_present(){
        UserAccount userAccount = new UserAccount(333,"First", "Name", new BigDecimal("100"),
                "GBP", "abc@abc.com", "street1");
        when(accountService.getUserAccount(333)).thenReturn(userAccount);

        assertThatExceptionOfType(AccountAlreadyExists.class)
                .isThrownBy(() ->  userAccountService.createUserAccount(userAccount))
                .withMessage("Account with id 333 already exists");
    }

    @Test
    void should_close_user_account(){
        UserAccount userAccount = new UserAccount(333,"First", "Name", BigDecimal.ZERO,
                "GBP", "abc@abc.com", "street1");
        when(accountService.getUserAccount(333)).thenReturn(userAccount);
        userAccountService.closeUserAccount(333);

        verify(accountService).closeAccount(333);
    }

    @Test
    void should_throw_invalid_account_exception_if_account_is_not_present_while_closing_account(){
        when(accountService.getUserAccount(100)).thenThrow(new InvalidAccountException("Account id 100 is not valid"));

        assertThatExceptionOfType(InvalidAccountException.class)
                .isThrownBy(() ->  userAccountService.closeUserAccount(100))
                .withMessage("Account id 100 is not valid");
    }

    @Test
    void should_throw_bad_request_exception_if_account_has_fund_available_while_closing_account(){
        when(accountService.getUserAccount(100)).thenThrow(new BadRequestException("Please transfer the funds from this account before closing"));

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() ->  userAccountService.closeUserAccount(100))
                .withMessage("Please transfer the funds from this account before closing");
    }
}