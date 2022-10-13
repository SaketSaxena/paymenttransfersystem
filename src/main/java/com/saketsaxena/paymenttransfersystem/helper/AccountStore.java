package com.saketsaxena.paymenttransfersystem.helper;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountStore {

    private static final List<AccountBalance> accountBalances = new ArrayList<>();

    public static List<AccountBalance> getAccountBalances(){
        accountBalances.add(new AccountBalance(111, new BigDecimal("100.10"), "GBP"));
        accountBalances.add(new AccountBalance(222, new BigDecimal("324.45"), "GBP"));
        return accountBalances;
    }
}
