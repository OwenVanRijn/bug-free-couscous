package io.swagger.dto;

import io.swagger.model.BankAccount;
import io.swagger.model.Limit;

public class BankaccountDTO {
    private String iban;
    private String name;
    private BankAccount.AccountTypeEnum accountType;
    private Double amount;
    private Limit balanceMin;

    public BankaccountDTO(BankAccount bankaccount) {
        iban = bankaccount.getIBAN();
        name = bankaccount.getName();
        accountType = bankaccount.getAccountType();
        amount = bankaccount.getAmountDecimal();
        balanceMin = bankaccount.getBalanceMin();
    }

    public BankaccountDTO() {
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BankAccount.AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(BankAccount.AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Limit getBalanceMin() {
        return balanceMin;
    }

    public void setBalanceMin(Limit balanceMin) {
        this.balanceMin = balanceMin;
    }
}
