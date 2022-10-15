package com.saketsaxena.paymenttransfersystem.exception;

/** Represents user account already exists exception.
 * @author Saket Saxena
 * @since 1.0
 */
public class AccountAlreadyExists extends BadRequestException{

    /** Creates an account already exists exception with the specified message.
     * @param message exception error message.
     */
    public AccountAlreadyExists(String message) {
        super(message);
    }
}
