package com.saketsaxena.paymenttransfersystem.DTOs;

import java.math.BigDecimal;

/** Creates an account balance with the specified account id.
 * @param senderAccountId account id of the sender who is transferring fund
 * @param receiverAccountId account id of the sender who is receiving fund
 * @param transferAmount amount need to transfer.
 */
public record PaymentTransfer(int senderAccountId, int receiverAccountId, BigDecimal transferAmount) {
}
