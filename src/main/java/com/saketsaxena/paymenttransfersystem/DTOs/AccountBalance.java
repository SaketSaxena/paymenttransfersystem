package com.saketsaxena.paymenttransfersystem.DTOs;

import java.math.BigDecimal;

public record AccountBalance(int accountId, BigDecimal balance, String currency) {
}
