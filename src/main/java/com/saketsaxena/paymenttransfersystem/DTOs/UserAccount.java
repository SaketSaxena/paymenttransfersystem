package com.saketsaxena.paymenttransfersystem.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

/** Class representation of user account.
 * @author Saket Saxena
 * @since 1.0
 */
public class UserAccount {
    /** Represents the user's account id.*/
    @JsonProperty("account-id")
    private final int accountId;
    /** Represents the user's first name.*/
    @JsonProperty("first-name")
    private final String firstName;
    /** Represents the user's last name.*/
    @JsonProperty("last-name")
    private final String lastName;
    /** Represents the user's balance.*/
    @JsonProperty("balance")
    private BigDecimal balance;
    /** Represents the user's balance amount currency.*/
    @JsonProperty("currency")
    private final String currency;
    /** Represents the user's email.*/
    @JsonProperty("email")
    private final String email;
    /** Represents the user's address.*/
    @JsonProperty("address")
    private final String address;
    /** Represents the user's account status (ACTIVE/DELETED).*/
    @JsonProperty("status")
    private AccountStatus status;

    /** Creates an UserAccount.
     * @param accountId user's account id.
     * @param firstName The user’s first name.
     * @param lastName The user’s last name.
     * @param balance The user’s account balance.
     * @param currency The user’s account balance currency.
     * @param email The user’s email.
     * @param address The user’s address.
     */
    public UserAccount(int accountId, String firstName, String lastName,
                       BigDecimal balance, String currency, String email, String address) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = Optional.ofNullable(balance).orElse(BigDecimal.ZERO);
        this.currency = Optional.ofNullable(currency).orElse("GBP");
        this.email = email;
        this.address = address;
        this.status = AccountStatus.ACTIVE;
    }

    /** Gets the user’s account id.
     * @return A int representing the user’s account id.
     */
    public int accountId() {
        return accountId;
    }

    /** Gets the user’s account balance.
     * @return A BigDecimal representing the user’s account balance.
     */
    public BigDecimal balance() {
        return balance;
    }

    /** Gets the user’s account balance currency.
     * @return A String representing the user’s account balance currency.
     */
    public String currency() {
        return currency;
    }

    /** Sets the user’s account balance.
     * @param balance takes user account balance in BigDecimal.
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /** Gets the user’s account status.
     * @return represents the user’s account status.
     */
    public AccountStatus getStatus() {
        return status;
    }

    /** Sets the user’s account status.
     * @param status takes user account status in AccountStatus.
     */
    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    /** equals implementation of user account object*/
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (UserAccount) obj;
        return this.accountId == that.accountId &&
                Objects.equals(this.firstName, that.firstName) &&
                Objects.equals(this.lastName, that.lastName) &&
                Objects.equals(this.balance, that.balance) &&
                Objects.equals(this.currency, that.currency) &&
                Objects.equals(this.email, that.email) &&
                Objects.equals(this.address, that.address);
    }

    /** hashcode implementation of user account object*/
    @Override
    public int hashCode() {
        return Objects.hash(accountId, firstName, lastName, balance, currency, email, address);
    }

    /** toString representation of user account object*/
    @Override
    public String toString() {
        return "UserAccount[" +
                "accountId=" + accountId + ", " +
                "firstName=" + firstName + ", " +
                "lastName=" + lastName + ", " +
                "balance=" + balance + ", " +
                "currency=" + currency + ", " +
                "email=" + email + ", " +
                "address=" + address + ']';
    }

}
