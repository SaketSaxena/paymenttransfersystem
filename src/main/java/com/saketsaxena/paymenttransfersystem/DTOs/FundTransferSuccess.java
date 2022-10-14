package com.saketsaxena.paymenttransfersystem.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FundTransferSuccess(@JsonProperty("success-message") String successMessage) {
}
