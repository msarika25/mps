package eu.mps.ibpts.domain.entity;
// Account.java

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private long id;
    private String currency;
    private BigDecimal balance;
    private List<Transaction> transactions;

    public Account(long id, String currency, BigDecimal balance) {
        this.id = id;
        this.currency = currency;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
