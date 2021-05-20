package io.swagger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.*;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class EmployeeEditUserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Address address;
    private User.RoleEnum role;

    @JsonProperty("BankAccounts")
    @Valid
    private List<BankAccount> bankAccounts;

    @JsonProperty("Limits")
    @Valid
    private List<Limit> limits;

    public EmployeeEditUserDTO() {
    }

    public EmployeeEditUserDTO firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public EmployeeEditUserDTO lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public EmployeeEditUserDTO email(String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeEditUserDTO phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EmployeeEditUserDTO address(Address address) {
        this.address = address;
        return this;
    }

    @Valid
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public EmployeeEditUserDTO role(User.RoleEnum role) {
        this.role = role;
        return this;
    }

    public User.RoleEnum getRole() {
        return role;
    }

    public void setRole(User.RoleEnum role) {
        this.role = role;
    }

    public EmployeeEditUserDTO bankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
        return this;
    }

    public EmployeeEditUserDTO addBankAccountsItem(BankAccount bankAccountsItem) {
        if (this.bankAccounts == null) {
            this.bankAccounts = new ArrayList<BankAccount>();
        }
        this.bankAccounts.add(bankAccountsItem);
        return this;
    }

    @Valid
    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public EmployeeEditUserDTO limits(List<Limit> limits) {
        this.limits = limits;
        return this;
    }

    public EmployeeEditUserDTO addLimitsItem(Limit limitsItem) {
        if (this.limits == null) {
            this.limits = new ArrayList<Limit>();
        }
        this.limits.add(limitsItem);
        return this;
    }

    @Valid
    public List<Limit> getLimits() {
        return limits;
    }

    public void setLimits(List<Limit> limits) {
        this.limits = limits;
    }

}
