package com.saketsaxena.paymenttransfersystem.exception;

/** Represents insufficient balance exception.
 * @author Saket Saxena
 * @since 1.0
 */
public class InsufficientBalanceException extends RuntimeException{

    /** Creates an insufficient balance exception with the specified message.
     * @param message exception error message.
     */
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
