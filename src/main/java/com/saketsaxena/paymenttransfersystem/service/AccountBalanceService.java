package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.saketsaxena.paymenttransfersystem.helper.AccountStore.getAccountBalances;


/** Service to perform operation related to account balance .
 * @author Saket Saxena
 * @since 1.0
 */
@Service
public class AccountBalanceService {


    /** Gets the account balance for the specified account id.
     * @param accountId account id to get account balance
     * @return An object representing the AccountBalance
     */
    public AccountBalance getAccountBalance(int accountId) {
        List<AccountBalance> accountBalances = getAccountBalances();

        return accountBalances.stream()
                .filter(accountBalance -> accountBalance.accountId() == accountId)
                .findFirst()
                .orElseThrow(() -> new InvalidAccountException(String.format("Account id %s is not valid", accountId)));
    }
}
