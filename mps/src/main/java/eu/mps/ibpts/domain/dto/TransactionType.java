package eu.mps.ibpts.domain.dto;

public enum TransactionType {
    DEBIT("Debit"),
    CREDIT("Credit");

    private final String transactionName;

    TransactionType(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTransactionName() {
        return transactionName;
    }
}
