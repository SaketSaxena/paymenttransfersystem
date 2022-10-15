package com.saketsaxena.paymenttransfersystem.controller;

import com.saketsaxena.paymenttransfersystem.DTOs.SuccessMessage;
import com.saketsaxena.paymenttransfersystem.DTOs.UserAccount;
import com.saketsaxena.paymenttransfersystem.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/account/{accountId}")
    public ResponseEntity<UserAccount> getUserAccount(@PathVariable int accountId) {
        return ResponseEntity.ok(userAccountService.getUserAccount(accountId));
    }

    /**
     * Rest endpoint to create new user account.
     *
     * @param userAccount A representation of UserAccount object.
     * @return A success response of object SuccessMessage,
     * bad request in case of account already exists with same id with the ErrorResponse object
     */
    @PostMapping("/account/new")
    public ResponseEntity<SuccessMessage> createNewUserAccount(@RequestBody UserAccount userAccount) {
        userAccountService.createUserAccount(userAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessMessage(String.format("Account successfully created with id %s", userAccount.accountId())));
    }

    /**
     * Rest endpoint to close user account.
     *
     * @param accountId id of account which has to be closed.
     * @return A success response of object UserAccount,
     * not found in case of account is invalid with the ErrorResponse object,
     * bad request in case of fund is available in account,
     */
    @PostMapping("/account/{accountId}/close")
    public ResponseEntity<SuccessMessage> closeUserAccount(@PathVariable int accountId) {
        userAccountService.closeUserAccount(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage(String.format("Account with id %s closed successfully", accountId)));
    }
}
