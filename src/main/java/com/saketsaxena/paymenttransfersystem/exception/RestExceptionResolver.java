package com.saketsaxena.paymenttransfersystem.exception;

import com.saketsaxena.paymenttransfersystem.DTOs.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

/** Represent a controller advice which can handle the exception
 * whenever any occurred while access rest apis.
 * @author Saket Saxena
 * @since 1.0
 */
@ControllerAdvice
public class RestExceptionResolver {

    /** Exception handler to handle invalid account exception.
     * @param invalidAccountException An object of InvalidAccountException
     * @return not found response in case of account is invalid,
     */
    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAccountException(InvalidAccountException invalidAccountException){
        ErrorResponse errorResponse = new ErrorResponse(invalidAccountException.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(errorResponse);
    }

    /** Exception handler to handle insufficient balance exception.
     * @param badRequestException An instance of BadRequestException | InsufficientBalanceException
     * @return bad request response if sender account is not having sufficient balance,
     * or sender-account-id and receiver-account-id is same.
     */
    @ExceptionHandler({BadRequestException.class, InsufficientBalanceException.class, AccountAlreadyExists.class})
    public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(BadRequestException badRequestException){
        ErrorResponse errorResponse = new ErrorResponse(badRequestException.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }


    /** Exception handler to handle common runtime exception.
     * @param runtimeException An object of RuntimeException
     * @return internal server response if any unwanted exception occurred while accessing apu
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException runtimeException){
        ErrorResponse errorResponse = new ErrorResponse(runtimeException.getMessage());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
