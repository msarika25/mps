package eu.mps.ibpts.controller;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import eu.mps.ibpts.domain.dto.AccountBalanceDTO;
import eu.mps.ibpts.domain.dto.TransferRequestDTO;
import eu.mps.ibpts.domain.entity.Account;
import eu.mps.ibpts.domain.entity.Transaction;
import eu.mps.ibpts.exception.IbptsException;
import eu.mps.ibpts.service.AccountService;
import eu.mps.ibpts.service.BankTransferService;
import eu.mps.ibpts.service.MiniStatementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class IBPTSControllerTest {

    @Mock
    private BankTransferService bankTransferService;

    @InjectMocks
    private IBPTSController bankTransferController;

    @Mock
    private AccountService accountService;

    @Mock
    private MiniStatementService miniStatementService;

    private TransferRequestDTO transferRequestDTO;
    private static final String CURRENCY = "EURO";
    @Before
    public void setup() {
        // Initialize transfer request object
        transferRequestDTO = new TransferRequestDTO(111L,222L, new BigDecimal(500), CURRENCY);
    }

    @Test
    public void testTransferSuccess() throws Exception {
        // Set up mock behavior for bankTransferService.transfer method
        when(bankTransferService.transfer(any(TransferRequestDTO.class))).thenReturn("Payment transfer successful");

        ResponseEntity<Object> response = bankTransferController.transfer(transferRequestDTO);

        // Verify that the response is a success and contains the correct message
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment transfer successful", response.getBody());
    }

    @Test
    public void testTransferFailure() throws Exception {
        // Set up mock behavior for bankTransferService.transfer method to throw exception
        when(bankTransferService.transfer(any(TransferRequestDTO.class))).thenThrow(new IbptsException("Invalid account"));

        ResponseEntity<Object> response = bankTransferController.transfer(transferRequestDTO);

        // Verify that the response is a bad request and contains the correct error message
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid account", response.getBody());
    }

    @Test
    public void testGetAccountBalance() throws IbptsException {
        long accountId = 1234;
        BigDecimal balance = new BigDecimal(1000);
        Account account = new Account(accountId, "John", balance);
        when(accountService.getAccountBalance(accountId)).thenReturn(account);

        ResponseEntity<Object> responseEntity = bankTransferController.getAccountBalance(accountId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        AccountBalanceDTO accountBalanceDTO = (AccountBalanceDTO) responseEntity.getBody();
        assertEquals(accountId, accountBalanceDTO.getAccountId());
        assertEquals(balance, accountBalanceDTO.getBalance());
    }

    @Test
    public void testGetAccountBalanceWithInvalidId() throws IbptsException {
        long accountId = 1234;
        when(accountService.getAccountBalance(accountId)).thenThrow(new IbptsException("Invalid account"));

        ResponseEntity<Object> responseEntity = bankTransferController.getAccountBalance(accountId);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid account", responseEntity.getBody());
    }

    @Test
    public void testGetMiniStatement() throws IbptsException {
        long accountId = 1234;
        int count = 5;
        List<Transaction> transactionList = new ArrayList<>();
        when(miniStatementService.getMiniStatement(accountId, count)).thenReturn(transactionList);

        ResponseEntity<Object> responseEntity = bankTransferController.getMiniStatement(accountId, count);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transactionList, responseEntity.getBody());
    }

    @Test
    public void testGetMiniStatementWithInvalidId() throws IbptsException {
        long accountId = 1234;
        int count = 5;
        when(miniStatementService.getMiniStatement(accountId, count)).thenThrow(new IbptsException("Invalid account"));

        ResponseEntity<Object> responseEntity = bankTransferController.getMiniStatement(accountId, count);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid account", responseEntity.getBody());
    }
}
