package com.saketsaxena.paymenttransfersystem.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record AccountBalance(@JsonProperty("account-id") int accountId, BigDecimal balance, String currency) {
}
