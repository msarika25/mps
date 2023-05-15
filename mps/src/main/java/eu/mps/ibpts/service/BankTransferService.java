package eu.mps.ibpts.service;

import eu.mps.ibpts.domain.dto.TransactionType;
import eu.mps.ibpts.domain.dto.TransferRequestDTO;
import eu.mps.ibpts.domain.entity.Account;
import eu.mps.ibpts.domain.entity.Transaction;
import eu.mps.ibpts.domain.repository.AccountRepository;
import eu.mps.ibpts.exception.IbptsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Transfer funds from sender to receiver
 * Check the accounts are debited and credited
 * Reject transfer if invalid receiver account details
 * Reject transfer if insufficient funds are available in sender account
 *
 */
@Service
public class BankTransferService {
private static final String PAYMENT_SUCCESS = "Payment transfer successful";
    @Autowired
    AccountRepository accountRepository;
    private List<Transaction> transactions = new ArrayList<>();
    public String transfer(TransferRequestDTO transferRequest) throws IbptsException {

        Account sender = accountRepository.getAccountById(transferRequest.getSenderAccountId());
        Account receiver = accountRepository.getAccountById(transferRequest.getReceiverAccountId());
        if (null == sender) {
            throw new IbptsException("Invalid sender account details " + transferRequest.getSenderAccountId());
        }
        if (null == receiver) {
            throw new IbptsException("Invalid receiver account details " + transferRequest.getReceiverAccountId());
        }
        if (sender.getBalance().compareTo(transferRequest.getAmount()) < 0) {
            throw new IbptsException("Insufficient funds available for " + sender.getId());
        }
        sender.setBalance(sender.getBalance().subtract(transferRequest.getAmount()));
        receiver.setBalance(receiver.getBalance().add(transferRequest.getAmount()));
        Transaction debitTransaction = new Transaction(transferRequest.getSenderAccountId(), sender.getBalance(), LocalDateTime.now(), TransactionType.DEBIT, transferRequest.getCurrency());
        Transaction creditTransaction = new Transaction(transferRequest.getReceiverAccountId(), receiver.getBalance(), LocalDateTime.now(), TransactionType.CREDIT, transferRequest.getCurrency());
        sender.addTransaction(debitTransaction);
        receiver.addTransaction(creditTransaction);
        transactions.add(debitTransaction);
        transactions.add(creditTransaction);

        accountRepository.updateBalance(receiver.getId(), receiver.getBalance());

        accountRepository.updateBalance(sender.getId(), sender.getBalance());

        return PAYMENT_SUCCESS;
    }

    public List<Transaction> getMiniStatement(Account account, int noOfTransactions) {

        List<Transaction> miniStatement = new ArrayList<>();
        int count = 0;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            if (Optional.ofNullable(account.getId()).isPresent()) {
                miniStatement.add(transaction);
                count++;
                if (count == noOfTransactions) {
                    break;
                }
            }
        }
        return miniStatement;
    }
}
