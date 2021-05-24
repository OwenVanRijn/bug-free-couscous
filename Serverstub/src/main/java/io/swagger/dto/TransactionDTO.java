package io.swagger.dto;

import io.swagger.model.Transaction;

import org.threeten.bp.OffsetDateTime;

public class TransactionDTO {
    private Long id;
    private Transaction.TypeEnum type;
    private String timestamp;
    private String ibanFrom;
    private String ibanTo;
    private Double amount;
    private PerformingTransactionUserDTO performedBy;

    public TransactionDTO(Transaction transaction){
        id = transaction.getId();
        type = transaction.getType();
        timestamp = transaction.getTimestamp().toString();
        ibanFrom = transaction.getIbANFrom();
        ibanTo = transaction.getIbANTo();
        amount = transaction.getAmountAsDecimal();
        performedBy = new PerformingTransactionUserDTO(transaction.getPerformedBy());
    }

    public Long getId() {
        return id;
    }

    public Transaction.TypeEnum getType() {
        return type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getIbanFrom() {
        return ibanFrom;
    }

    public String getIbanTo() {
        return ibanTo;
    }

    public Double getAmount() {
        return amount;
    }

    public PerformingTransactionUserDTO getPerformedBy() {
        return performedBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(Transaction.TypeEnum type) {
        this.type = type;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setIbanFrom(String ibanFrom) {
        this.ibanFrom = ibanFrom;
    }

    public void setIbanTo(String ibanTo) {
        this.ibanTo = ibanTo;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setPerformedBy(PerformingTransactionUserDTO performedBy) {
        this.performedBy = performedBy;
    }
}
