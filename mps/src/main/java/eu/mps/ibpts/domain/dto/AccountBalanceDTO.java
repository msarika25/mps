package eu.mps.ibpts.domain.dto;

import java.math.BigDecimal;

public class AccountBalanceDTO {

    private long accountId;
    private BigDecimal balance;
    private String currency;

    public AccountBalanceDTO(long accountId, BigDecimal balance, String currency) {
        this.accountId = accountId;
        this.balance = balance;
        this.currency = currency;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

