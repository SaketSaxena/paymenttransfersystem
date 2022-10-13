package com.saketsaxena.paymenttransfersystem.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/** Creates a mini statement object with the transactoin details.
 * @param accountId account id of the user
 * @param amount amount associated with the particular transaction.
 * @param currency denotes the currency of the transaction amount.
 * @param type denotes the transaction type (CREDIT/DEBIT).
 * @param transactionDate denotes the date and time when the transaction happen.
 */
public record MiniStatement(@JsonProperty("account-id") int accountId, BigDecimal amount,
                            String currency, TransactionType type,
                            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
                            @JsonProperty("transaction-date")
                            ZonedDateTime transactionDate) {
}
