package io.swagger.dto;

import io.swagger.model.BankAccount;
import io.swagger.v3.oas.annotations.media.Schema;

public class CreateBankaccountDTO {
    private String name;
    private BankAccount.AccountTypeEnum type;

    public CreateBankaccountDTO() {
    }
    @Schema(example = "Daily account")
    public String getName(){return name;}

    public void setName(String name) {
        this.name = name;
    }

    @Schema(example = "Current")
    public BankAccount.AccountTypeEnum getAccountType(){return type;}

    public void setAccountType(BankAccount.AccountTypeEnum type) {this.type = type;}

    public CreateBankaccountDTO name(String name){
        this.name = name;
        return this;
    }

    public CreateBankaccountDTO type(BankAccount.AccountTypeEnum type){
        this.type = type;
        return this;
    }

}
