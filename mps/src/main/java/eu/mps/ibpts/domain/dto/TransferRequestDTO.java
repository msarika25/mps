package eu.mps.ibpts.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferRequestDTO implements Serializable {
    private long senderAccountId;
    private long receiverAccountId;
    private BigDecimal amount;
    private String currency;

    public TransferRequestDTO(long senderAccountId, long receiverAccountId, BigDecimal amount, String currency) {
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
        this.currency = currency;
    }

    public long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(int senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public long getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(int receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

