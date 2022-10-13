package com.saketsaxena.paymenttransfersystem.exception;

/** Represents invalid account exception.
 * @author Saket Saxena
 * @since 1.0
 */
public class InvalidAccountException extends RuntimeException {

    /** Creates an employee with the specified name.
     * @param message exception error message.
     */
    public InvalidAccountException(String message) {
        super(message);
    }
}
