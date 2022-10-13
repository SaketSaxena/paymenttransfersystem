package com.saketsaxena.paymenttransfersystem.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/** Creates an account balance with the specified account id.
 * @param accountId account id of the user
 * @param balance current balance in the account.
 * @param currency denotes the currency of the amount present in the account.
 */
public record AccountBalance(@JsonProperty("account-id") int accountId, BigDecimal balance, String currency) {
}
