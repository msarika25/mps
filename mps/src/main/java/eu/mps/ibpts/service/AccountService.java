package eu.mps.ibpts.service;

import eu.mps.ibpts.domain.entity.Account;
import eu.mps.ibpts.domain.repository.AccountRepository;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private Map<String, Account> accounts;

    public AccountService() {
        this.accounts = new HashMap<>();
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    public void updateAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    public Account getAccountById(String accountId) throws InvalidAccountException {
        if (!accounts.containsKey(accountId)) {
            throw new InvalidAccountException("Invalid account ID");
        }
        return accounts.get(accountId);
    }
}
