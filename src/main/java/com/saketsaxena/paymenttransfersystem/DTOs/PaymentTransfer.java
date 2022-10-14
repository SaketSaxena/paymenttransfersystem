package com.saketsaxena.paymenttransfersystem.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/** Creates an account balance with the specified account id.
 * @param senderAccountId account id of the sender who is transferring fund
 * @param receiverAccountId account id of the sender who is receiving fund
 * @param transferAmount amount need to transfer.
 */
public record PaymentTransfer(@JsonProperty("sender-account-id") int senderAccountId,
                              @JsonProperty("receiver-account-id") int receiverAccountId,
                              @JsonProperty("transfer-amount") BigDecimal transferAmount) {
}
