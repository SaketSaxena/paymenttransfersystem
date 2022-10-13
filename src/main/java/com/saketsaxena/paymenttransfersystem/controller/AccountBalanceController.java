package com.saketsaxena.paymenttransfersystem.controller;

import com.saketsaxena.paymenttransfersystem.DTOs.ErrorResponse;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import com.saketsaxena.paymenttransfersystem.service.AccountBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
public class AccountBalanceController {

    private final AccountBalanceService accountBalanceService;

    @Autowired
    public AccountBalanceController(AccountBalanceService accountBalanceService) {
        this.accountBalanceService = accountBalanceService;
    }

    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<?> getAccountBalance(@PathVariable int accountId){
        try {
            return ResponseEntity.ok(accountBalanceService.getAccountBalance(accountId));
        } catch (InvalidAccountException invalidAccountException) {
            ErrorResponse errorResponse = new ErrorResponse(invalidAccountException.getMessage());
            return ResponseEntity.status(NOT_FOUND).body(errorResponse);
        }
    }
}