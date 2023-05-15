package eu.mps.ibpts.service;

import eu.mps.ibpts.domain.entity.Account;
import eu.mps.ibpts.domain.repository.AccountRepository;
import eu.mps.ibpts.exception.IbptsException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;

    private static final String CURRENCY ="EURO";

    @Test
    public void getAccountBalance_shouldReturnAccountBalance() throws IbptsException {
        // Arrange
        long accountId = 111L;
        BigDecimal balance = new BigDecimal(1000);
        Account account = new Account(accountId, CURRENCY, balance);
        when(accountRepository.getAccountBalance(accountId)).thenReturn(account);

        // Act
        Account result = accountService.getAccountBalance(accountId);

        // Assert
        assertEquals(accountId, result.getId());
        assertEquals(balance, result.getBalance());
    }

    @Test
    public void getAccountBalance_shouldThrowExceptionForInvalidAccount() {
        long accountId = 456L;
        try {
            accountService.getAccountBalance(accountId);
        } catch (IbptsException e) {
            Assert.assertEquals("Account with id " + accountId + " not found", e.getMessage());
        }
    }

    @Test
    public void getAccount_shouldReturnAccount() throws IbptsException {
        // Arrange
        long accountId = 222L;
        Account account = new Account(accountId, CURRENCY, new BigDecimal(500));
        when(accountRepository.getAccountById(accountId)).thenReturn(account);

        // Act
        Account result = accountService.getAccount(accountId);

        // Assert
        assertEquals(accountId, result.getId());
    }
}
