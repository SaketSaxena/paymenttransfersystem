package com.saketsaxena.paymenttransfersystem.controller;

import com.saketsaxena.paymenttransfersystem.DTOs.UserAccount;
import com.saketsaxena.paymenttransfersystem.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to publish rest api to manage user account.
 *
 * @author Saket Saxena
 * @since 1.0
 */
@RestController
public class UserAccountController {

   private final UserAccountService userAccountService;

   @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    /**
     * Rest endpoint to get user account details.
     *
     * @param accountId account id to get user account.
     * @return A success response of object UserAccount,
     * not found in case of account is invalid with the ErrorResponse object
     */
    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<UserAccount> getUserAccount(@PathVariable int accountId) {
        return ResponseEntity.ok(userAccountService.getUserAccount(accountId));
    }
}
