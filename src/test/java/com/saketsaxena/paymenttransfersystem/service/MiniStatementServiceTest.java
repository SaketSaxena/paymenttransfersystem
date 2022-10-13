package com.saketsaxena.paymenttransfersystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.saketsaxena.paymenttransfersystem.DTOs.TransactionType.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MiniStatementServiceTest {

    @Autowired
    private MiniStatementService miniStatementService;

    @BeforeEach
    void setUp(){
        miniStatementService.miniStatements.clear();
    }

    @Test
    void should_add_first_transaction_to_mini_statement(){
        miniStatementService.addTransactionToMiniStatement(111, new BigDecimal("20"), "GBP", DEBIT);
        assertThat(miniStatementService.getMiniStatement(111).size()).isEqualTo(1);
    }

    @Test
    void should_add_more_than_one_transaction_to_mini_statement(){
        miniStatementService.addTransactionToMiniStatement(111, new BigDecimal("20"), "GBP", DEBIT);
        miniStatementService.addTransactionToMiniStatement(111, new BigDecimal("20"), "GBP", CREDIT);
        assertThat(miniStatementService.getMiniStatement(111).size()).isEqualTo(2);
    }
}