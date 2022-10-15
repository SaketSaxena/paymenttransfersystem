package com.saketsaxena.paymenttransfersystem.exception;

/** Represents bad request exception.
 * @author Saket Saxena
 * @since 1.0
 */
public class BadRequestException extends RuntimeException{

    /** Creates an bad request exception with the specified message.
     * @param message exception error message.
     */
    public BadRequestException(String message) {
        super(message);
    }
}
