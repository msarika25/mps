package eu.mps.ibpts.domain.entity;

import eu.mps.ibpts.domain.dto.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private long accountId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private TransactionType transactionType;
    private String currency;

    public Transaction(long accountId, BigDecimal amount, LocalDateTime transactionDate, TransactionType transactionType, String currency) {
        this.accountId = accountId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.currency =currency;
    }
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public long getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
