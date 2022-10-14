package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/** Service to perform operation related to account balance .
 * @author Saket Saxena
 * @since 1.0
 */
@Service
public class AccountBalanceService {

    @Qualifier("accountServiceInMemoryImpl")
    private final AccountService accountService;

    @Autowired
    public AccountBalanceService(AccountServiceInMemoryImpl accountService) {
        this.accountService = accountService;
    }

    /** Gets the account balance for the specified account id.
     * @param accountId account id to get account balance
     * @return An object representing the AccountBalance
     */
    public AccountBalance getAccountBalance(int accountId) {
      return accountService.getAccountBalances().values().stream()
                .filter(accountBalance -> accountBalance.accountId() == accountId)
                .findFirst()
                .orElseThrow(() -> new InvalidAccountException(String.format("Account id %s is not valid", accountId)));
    }
}
