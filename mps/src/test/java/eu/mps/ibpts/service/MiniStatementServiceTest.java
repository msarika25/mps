package eu.mps.ibpts.service;

import eu.mps.ibpts.domain.dto.TransactionType;
import eu.mps.ibpts.domain.entity.Account;
import eu.mps.ibpts.domain.entity.Transaction;
import eu.mps.ibpts.exception.IbptsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MiniStatementServiceTest {
    @Mock
    private AccountService accountService;

    @Mock
    private BankTransferService bankTransferService;

    @InjectMocks
    private MiniStatementService miniStatementService;
    private static final String CURRENCY = "EURO";
    @Test
    public void testGetMiniStatement() throws IbptsException {
        Account sender = new Account(111L, CURRENCY, new BigDecimal(1000));
        Account receiver = new Account(222L, CURRENCY, new BigDecimal(500));
        when(accountService.getAccount(sender.getId())).thenReturn(sender);

        List<Transaction> transactions = new ArrayList<>();
        Transaction debit = new Transaction(sender.getId(), new BigDecimal("50"), LocalDateTime.now(), TransactionType.DEBIT, CURRENCY);
        Transaction credit = new Transaction(receiver.getId(), new BigDecimal("50"), LocalDateTime.now(), TransactionType.CREDIT, CURRENCY);

        transactions.add(debit);
        transactions.add(credit);
        when(bankTransferService.getMiniStatement(sender, 1)).thenReturn(transactions);

        List<Transaction> miniStatement = miniStatementService.getMiniStatement(sender.getId(), 1);
        assertEquals(2, miniStatement.size());
        assertEquals(sender.getId(), miniStatement.get(0).getAccountId());
        assertEquals(receiver.getId(), miniStatement.get(1).getAccountId());
        assertEquals(new BigDecimal("50"), miniStatement.get(0).getAmount());
    }

    @Test(expected = IbptsException.class)
    public void testGetMiniStatementWithInvalidAccountId() throws IbptsException {
        long invalidAccountId = 100;
        when(accountService.getAccount(invalidAccountId)).thenReturn(null);
        miniStatementService.getMiniStatement(invalidAccountId, 1);
    }
}
