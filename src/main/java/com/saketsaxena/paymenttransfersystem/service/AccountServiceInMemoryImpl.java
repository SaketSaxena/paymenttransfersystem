package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountStatus;
import com.saketsaxena.paymenttransfersystem.DTOs.UserAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/** Represents in-memory database to store account balance.
 * @author Saket Saxena
 * @since 1.0
 */
@Service
public class AccountServiceInMemoryImpl implements AccountService {

    /** Represents the map of all the user account.
     */
    private final Map<Integer, UserAccount> userAccounts = new HashMap<>();
    public AccountServiceInMemoryImpl() {
        userAccounts.put(111, new UserAccount(111, "First", "Name", new BigDecimal("100.10"), "GBP", "abc@abc.com", "street1"));
        userAccounts.put(222, new UserAccount(222, "John", "Wick", new BigDecimal("324.45"), "GBP", "def@abc.com", "street2"));
        userAccounts.put(786, new UserAccount(786, "John", "Wick", new BigDecimal("324.45"), "GBP", "def@abc.com", "street2"));
    }

    /**
     * Crate User account with specified account details.
     * @param userAccount Representation of UserAccount object.
     */
    @Override
    public void createUserAccount(UserAccount userAccount) {
        userAccounts.put(userAccount.accountId(), userAccount);
    }

    /**
     * Get User account for specified accountId.
     * @param accountId account id of the user
     * @return Optional object of UserAccount.
     */
    @Override
    public Optional<UserAccount> getUserAccount(int accountId) {
        return Optional.ofNullable(userAccounts.get(accountId));
    }

    /**
     * Get only active User account for specified accountId.
     * @param accountId account id of the user
     * @return Optional object of UserAccount.
     */
    @Override
    public Optional<UserAccount> getActiveUserAccount(int accountId) {
        return Optional.ofNullable(userAccounts.get(accountId))
                .filter(userAccount -> userAccount.getStatus() == AccountStatus.ACTIVE);
    }

    /** Method to find out in the account is having insufficient balance.
     * @param accountId account id for which balance needs to be found.
     * @param amount amount which needs to be checked against the balance available.
     * @return boolean, true if balance is insufficient and false in balance is sufficient.
     */
    @Override
    public boolean isInsufficientBalance(int accountId, BigDecimal amount) {
        return userAccounts.get(accountId).balance().compareTo(amount) < 0;
    }

    /**
     * To check if the account is present or not.
     * @return A boolean, true if account is present and active in the system
     * and false is it is not present.
     */
    @Override
    public boolean isValidAccount(int accountId) {
        return getActiveUserAccount(accountId).isPresent();
    }

    /**
     * update account balance for specified account id.
     * @param accountId account id for which balance needs to be updated.
     * @param accountBalance new balance of user account.
     */
    @Override
    public void updateAccountBalance(int accountId, BigDecimal accountBalance){
        UserAccount updateUserAccount = userAccounts.get(accountId);
        updateUserAccount.setBalance(accountBalance);
        userAccounts.put(accountId, updateUserAccount);
    }

    /**
     * close user account for specified account id.
     * @param accountId account id of account which needs to deleted.
     */
    @Override
    public void closeAccount(int accountId) {
        UserAccount updateUserAccount = userAccounts.get(accountId);
        updateUserAccount.setStatus(AccountStatus.DELETED);
        userAccounts.put(accountId, updateUserAccount);
    }
}
