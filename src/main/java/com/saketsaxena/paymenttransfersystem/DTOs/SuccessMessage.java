package com.saketsaxena.paymenttransfersystem.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SuccessMessage(@JsonProperty("success-message") String successMessage) {
}
