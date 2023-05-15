package eu.mps.ibpts.service;

import eu.mps.ibpts.domain.entity.Account;
import eu.mps.ibpts.domain.entity.Transaction;
import eu.mps.ibpts.exception.IbptsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Get the mini statement
 * invalid account number check
 */

@Service
public class MiniStatementService {
    @Autowired
    AccountService accountService;

    @Autowired
    BankTransferService bankTransferService;

    public List<Transaction> getMiniStatement(long accountId, int count) throws IbptsException {
        Account account = accountService.getAccount(accountId);
        if(account==null)
            throw new IbptsException("Account with id " + accountId + " not found");

        return bankTransferService.getMiniStatement(account, count);
    }
}
