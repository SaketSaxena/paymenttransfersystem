package com.saketsaxena.paymenttransfersystem.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Creates an error response with the specified error message.
 * @param errorMessage error message with needs to send to the user
 */
public record ErrorResponse(@JsonProperty("error-message") String errorMessage) {
}
