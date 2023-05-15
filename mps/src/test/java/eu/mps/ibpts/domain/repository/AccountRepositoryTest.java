package eu.mps.ibpts.domain.repository;

import eu.mps.ibpts.domain.entity.Account;
import eu.mps.ibpts.exception.IbptsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountRepositoryTest {

    private AccountRepository accountRepository;

    @Before
    public void setUp() {
        accountRepository = new AccountRepository();
    }

    @Test
    public void testGetAccountById() {
        Account account = accountRepository.getAccountById(111L);
        assertNotNull(account);
        assertEquals(new BigDecimal(1000), account.getBalance());

        account = accountRepository.getAccountById(222L);
        assertNotNull(account);
        assertEquals(new BigDecimal(500), account.getBalance());

        account = accountRepository.getAccountById(333L);
        assertNull(account);
    }

    @Test
    public void testUpdateBalance() {
        try {
            accountRepository.updateBalance(333L, new BigDecimal(500));
        } catch (IbptsException e) {
            assertTrue(e.getMessage().contains("not found"));
        }
    }

    @Test
    public void testGetAccountBalance() {
        try {
            Account account = accountRepository.getAccountBalance(111L);
            assertEquals(new BigDecimal(1000), account.getBalance());
            accountRepository.getAccountBalance(333L);
        } catch (IbptsException e) {
            assertTrue(e.getMessage().contains("not found"));
        }
    }
}
