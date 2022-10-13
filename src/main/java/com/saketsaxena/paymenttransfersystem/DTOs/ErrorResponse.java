package com.saketsaxena.paymenttransfersystem.DTOs;

/** Creates an error response with the specified error message.
 * @param errorMessage error message with needs to send to the user
 */
public record ErrorResponse(String errorMessage) {
}
