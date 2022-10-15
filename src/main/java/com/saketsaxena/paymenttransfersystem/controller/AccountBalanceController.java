package com.saketsaxena.paymenttransfersystem.controller;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;
import com.saketsaxena.paymenttransfersystem.DTOs.MiniStatement;
import com.saketsaxena.paymenttransfersystem.service.AccountBalanceService;
import com.saketsaxena.paymenttransfersystem.service.MiniStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Queue;

/**
 * Controller to publish rest api to know balance and mini statement.
 *
 * @author Saket Saxena
 * @since 1.0
 */
@RestController
public class AccountBalanceController {

    private final AccountBalanceService accountBalanceService;
    private final MiniStatementService miniStatementService;

    @Autowired
    public AccountBalanceController(AccountBalanceService accountBalanceService,
                                    MiniStatementService miniStatementService) {
        this.accountBalanceService = accountBalanceService;
        this.miniStatementService = miniStatementService;
    }

    /**
     * Rest endpoint to get the balance for the account.
     *
     * @param accountId account id to get account balance
     * @return A success response of object AccountBalance,
     * not found in case of account is invalid with the ErrorResponse object
     */
    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<AccountBalance> getAccountBalance(@PathVariable int accountId) {
            return ResponseEntity.ok(accountBalanceService.getAccountBalance(accountId));
    }

    /**
     * Rest endpoint to get the mini statement for the account.
     *
     * @param accountId as path variable for which mini statement needs to be fetched.
     * @return A response of object Queue<MiniStatement>,
     * or object of ErrorResponse in case of account is invalid
     */
    @GetMapping("/accounts/{accountId}/statements/mini")
    public ResponseEntity<Queue<MiniStatement>> getAccountMiniStatement(@PathVariable int accountId) {
        return ResponseEntity.ok(miniStatementService.getMiniStatement(accountId));
    }
}
