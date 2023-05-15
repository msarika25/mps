package eu.mps.ibpts.domain.repository;

import eu.mps.ibpts.domain.entity.Account;
import eu.mps.ibpts.exception.IbptsException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Used in-memory data-structure to store all the data
 * Get the account balance
 * Get account details
 * update accounts with debit and credit amount
 * Invalid account check
 *  */

@Repository
public class AccountRepository {
    private Map<Long, Account> accountBalances;
    private List<Account> accounts;
    private static final String CURRENCY="EUR";

    public AccountRepository() {
        // Initialize account balances with some sample data
        accountBalances = new HashMap<>();
        accountBalances.put(111L, new Account( 111L, CURRENCY, new BigDecimal(1000)));
        accountBalances.put(222L, new Account(222L, CURRENCY, new BigDecimal(500)));

        // Initialize list of accounts with the same sample data
        accounts = new ArrayList<>();
        accounts.add(new Account(111L, CURRENCY, new BigDecimal(1000)));
        accounts.add(new Account(222L, CURRENCY, new BigDecimal(500)));
    }

    public Account getAccountById(long id) {
        return accounts.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Method to update account balance by account id
    public void updateBalance(long accountId, BigDecimal amount) throws IbptsException {
        if (!accountBalances.containsKey(accountId)) {
            throw new IbptsException("Account with id " + accountId + " not found");
        }
        accountBalances.put(accountId, new Account(accountId, CURRENCY, amount));
    }

    public Account getAccountBalance(long accountId) throws IbptsException {
        if (!accountBalances.containsKey(accountId)) {
            throw new IbptsException("Account with id " + accountId + " not found");
        }
        return accountBalances.get(accountId);
    }


}

