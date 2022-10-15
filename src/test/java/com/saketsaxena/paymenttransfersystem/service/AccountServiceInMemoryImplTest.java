package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.UserAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.saketsaxena.paymenttransfersystem.DTOs.AccountStatus.ACTIVE;
import static com.saketsaxena.paymenttransfersystem.DTOs.AccountStatus.DELETED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AccountServiceInMemoryImplTest {

    @Autowired
    private AccountService accountServiceInMemoryImpl;

    @Test
    void should_return_true_if_account_is_valid() {
        assertTrue(accountServiceInMemoryImpl.isValidAccount(111));
    }

    @Test
    void should_return_false_if_account_is_not_present_in_the_sysyem() {
        assertFalse(accountServiceInMemoryImpl.isValidAccount(1111));
    }

    @Test
    void should_return_false_if_account_is_deleted() {
        accountServiceInMemoryImpl.closeAccount(786);
        assertFalse(accountServiceInMemoryImpl.isValidAccount(786));
    }

    @Test
    void should_get_user_account() {
        assertThat(accountServiceInMemoryImpl.getUserAccount(111)).isPresent()
                .get()
                .extracting("accountId", "firstName", "lastName", "email", "balance")
                .contains(111, "First", "Name", "abc@abc.com", new BigDecimal("100.10"));
    }

    @Test
    void should_update_account_balance(){
        accountServiceInMemoryImpl.updateAccountBalance(111, new BigDecimal("80"));

        assertThat(accountServiceInMemoryImpl.getUserAccount(111)).isPresent()
                .get()
                .extracting("accountId", "balance", "currency")
                .contains(111, new BigDecimal("80"), "GBP");
    }

    @Test
    void should_create_new_user_account(){
        UserAccount newUserAccount = new UserAccount(333, "First", "Name", new BigDecimal("100.10"), "GBP", "abc@abc.com", "street1");
        accountServiceInMemoryImpl.createUserAccount(newUserAccount);

        assertThat(accountServiceInMemoryImpl.getUserAccount(333)).isPresent()
                .get()
                .extracting("accountId", "firstName", "lastName", "email", "balance")
                .contains(333, "First", "Name", "abc@abc.com", new BigDecimal("100.10"));
    }

    @Test
    void should_close_user_account(){
        UserAccount newUserAccount = new UserAccount(333, "First", "Name", new BigDecimal("100.10"), "GBP", "abc@abc.com", "street1");

        accountServiceInMemoryImpl.createUserAccount(newUserAccount);

        assertThat(accountServiceInMemoryImpl.getUserAccount(333)).isPresent()
                .get()
                .extracting("accountId", "firstName", "lastName", "email", "balance", "status")
                .contains(333, "First", "Name", "abc@abc.com", new BigDecimal("100.10"), ACTIVE);

        accountServiceInMemoryImpl.closeAccount(333);

        assertThat(accountServiceInMemoryImpl.getUserAccount(333)).isPresent()
                .get()
                .extracting("accountId", "firstName", "lastName", "email", "balance", "status")
                .contains(333, "First", "Name", "abc@abc.com", new BigDecimal("100.10"), DELETED);

    }

}