package com.saketsaxena.paymenttransfersystem.exception;

/** Represents invalid account exception.
 * @author Saket Saxena
 * @since 1.0
 */
public class InvalidAccountException extends RuntimeException {

    /** Creates an invalid account exception with the specified message.
     * @param message exception error message.
     */
    public InvalidAccountException(String message) {
        super(message);
    }
}
