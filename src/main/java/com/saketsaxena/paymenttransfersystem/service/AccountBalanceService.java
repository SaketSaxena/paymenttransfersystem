package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import com.saketsaxena.paymenttransfersystem.helper.AccountStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service to perform operation related to account balance .
 * @author Saket Saxena
 * @since 1.0
 */
@Service
public class AccountBalanceService {

    private final AccountStore accountStore;

    @Autowired
    public AccountBalanceService(AccountStore accountStore) {
        this.accountStore = accountStore;
    }

    /** Gets the account balance for the specified account id.
     * @param accountId account id to get account balance
     * @return An object representing the AccountBalance
     */
    public AccountBalance getAccountBalance(int accountId) {
      return accountStore.getAccountBalances().values().stream()
                .filter(accountBalance -> accountBalance.accountId() == accountId)
                .findFirst()
                .orElseThrow(() -> new InvalidAccountException(String.format("Account id %s is not valid", accountId)));
    }
}
