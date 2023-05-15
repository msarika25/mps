package eu.mps.ibpts.service;

import eu.mps.ibpts.domain.entity.Account;
import eu.mps.ibpts.domain.repository.AccountRepository;
import eu.mps.ibpts.exception.IbptsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Get the account balance
 * Get account details
 * update accounts with debit and credit amount
 */

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account getAccountBalance(long accountId) throws IbptsException {
        return accountRepository.getAccountBalance(accountId);
    }

    public Account getAccount(long accountId){
        return accountRepository.getAccountById(accountId);
    }

    public void updateAccount(long accountId, BigDecimal amount) throws IbptsException {
         accountRepository.updateBalance(accountId, amount);
    }

}
