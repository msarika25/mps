package eu.mps.ibpts.service;

import eu.mps.ibpts.domain.dto.TransferRequestDTO;
import eu.mps.ibpts.domain.entity.Account;
import eu.mps.ibpts.domain.entity.Transaction;
import eu.mps.ibpts.domain.repository.AccountRepository;
import eu.mps.ibpts.exception.IbptsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankTransferServiceTest {
    @InjectMocks
    private BankTransferService bankTransferService;
    @Mock
    private AccountRepository accountRepository;
    private List<Transaction> transactions;
    private static final String CURRENCY = "EURO";
    private Account sender;
    private Account receiver;

    @Before
    public void setUp() {
        sender = new Account(111L, CURRENCY, new BigDecimal(1000));
        receiver = new Account(222L, CURRENCY, new BigDecimal(500));
        when(accountRepository.getAccountById(sender.getId())).thenReturn(sender);
        when(accountRepository.getAccountById(receiver.getId())).thenReturn(receiver);
    }

    @Test
    public void testTransfer() throws Exception {
        TransferRequestDTO transferRequest = new TransferRequestDTO(sender.getId(), receiver.getId(), new BigDecimal(500), CURRENCY);
        String result = bankTransferService.transfer(transferRequest);

        assertEquals("Payment transfer successful", result);
        assertEquals(new BigDecimal(500), sender.getBalance());
        assertEquals(new BigDecimal(1000), receiver.getBalance());
        assertEquals(1, sender.getTransactions().size());
        assertEquals(1, receiver.getTransactions().size());
    }

    @Test
    public void testTransferWithInvalidSenderAccount() {
        TransferRequestDTO transferRequest = new TransferRequestDTO(sender.getId(), receiver.getId(), new BigDecimal(500), CURRENCY);
        try {
            bankTransferService.transfer(transferRequest);
        }catch (IbptsException e)
        {
            assertEquals("Invalid sender account details " + transferRequest.getSenderAccountId(), e.getMessage());
        }
    }

    @Test
    public void testTransferWithInvalidReceiverAccount(){
        TransferRequestDTO transferRequest = new TransferRequestDTO(sender.getId(), 999L, new BigDecimal(500), CURRENCY);
        try {
            bankTransferService.transfer(transferRequest);
        }catch (IbptsException e)
        {
            assertEquals("Invalid receiver account details " + transferRequest.getReceiverAccountId(), e.getMessage());
        }
    }

    @Test
    public void testTransferWithInsufficientFunds(){
        try {
            TransferRequestDTO transferRequest = new TransferRequestDTO(sender.getId(), receiver.getId(), new BigDecimal(1010), CURRENCY);
            bankTransferService.transfer(transferRequest);
        }catch (IbptsException e)
        {
            assertEquals("Insufficient funds available for " + sender.getId(), e.getMessage());
        }
    }

    @Test
    public void testGetMiniStatement() throws Exception {

        TransferRequestDTO transferRequest = new TransferRequestDTO(sender.getId(), receiver.getId(), new BigDecimal(500), CURRENCY);
        bankTransferService.transfer(transferRequest);

        List<Transaction> miniStatement = bankTransferService.getMiniStatement(sender, 1);

        assertEquals(1, miniStatement.size());
        assertEquals(receiver.getId(), miniStatement.get(0).getAccountId());
    }
}
