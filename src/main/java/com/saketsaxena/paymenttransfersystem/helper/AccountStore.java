package com.saketsaxena.paymenttransfersystem.helper;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Represents in-memory database to store account balance.
 * @author Saket Saxena
 * @since 1.0
 */
@Service
public class AccountStore {

    /** Represents the map of all the account balances.
     */
    private final Map<Integer, AccountBalance> accountBalances = new HashMap<>();

    public AccountStore() {
        accountBalances.put(111, new AccountBalance(111, new BigDecimal("100.10"), "GBP"));
        accountBalances.put(222, new AccountBalance(222, new BigDecimal("324.45"), "GBP"));
    }

    /** Get all account balance .
     * @return A List representing the AccountBalance of all the users.
     */
    public List<AccountBalance> getAccountBalances() {
        return accountBalances.values().stream().toList();
    }
}
