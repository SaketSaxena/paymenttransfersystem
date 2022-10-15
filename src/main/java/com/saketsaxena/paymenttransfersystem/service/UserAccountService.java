package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.UserAccount;
import com.saketsaxena.paymenttransfersystem.exception.AccountAlreadyExists;
import com.saketsaxena.paymenttransfersystem.exception.BadRequestException;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Service to perform operation related to user account management.
 *
 * @author Saket Saxena
 * @since 1.0
 */
@Service
public class UserAccountService {

    @Qualifier("accountServiceInMemoryImpl")
    private final AccountService accountService;

    @Autowired
    public UserAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Gets the user account for the specified account id.
     *
     * @param accountId account id to get account balance
     * @return An object representing the UserAccount
     */
    public UserAccount getUserAccount(int accountId) {
        return accountService.getUserAccount(accountId)
                .orElseThrow(() -> new InvalidAccountException(String.format("Account id %s is invalid", accountId)));
    }

    /**
     * Crate User account with specified account details.
     * @param userAccount Representation of UserAccount object.
     */
    public void createUserAccount(UserAccount userAccount) {
        Optional<UserAccount> account = accountService.getUserAccount(userAccount.accountId());
        if(account.isPresent()){
            throw new AccountAlreadyExists(String.format("Account with id %s already exists", userAccount.accountId()));
        }
        accountService.createUserAccount(userAccount);
    }

    /**
     * close user account for specified account id.
     * @param accountId account id of account which needs to deleted.
     */
    public void closeUserAccount(int accountId) {
        Optional<UserAccount> userAccount = accountService.getUserAccount(accountId);
        if (userAccount.isEmpty()){
            throw new InvalidAccountException(String.format("Account id %s is not valid",accountId));
        } else if (userAccount.get().balance().compareTo(BigDecimal.ZERO) > 0 ){
            throw new BadRequestException("Please transfer the funds from this account before closing");
        }
        accountService.closeAccount(accountId);
    }
}
