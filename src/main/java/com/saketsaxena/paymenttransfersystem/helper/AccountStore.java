package com.saketsaxena.paymenttransfersystem.helper;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Represents in-memory database to store account balance.
 * @author Saket Saxena
 * @since 1.0
 */
public class AccountStore {

    /** Represents the list of all the account balances.
     */
    private static final List<AccountBalance> accountBalances = new ArrayList<>();

    /** Get all account balance .
     * @return A List representing the AccountBalance of all the users.
     */
    public static List<AccountBalance> getAccountBalances(){
        accountBalances.add(new AccountBalance(111, new BigDecimal("100.10"), "GBP"));
        accountBalances.add(new AccountBalance(222, new BigDecimal("324.45"), "GBP"));
        return accountBalances;
    }
}
