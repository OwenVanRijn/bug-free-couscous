package io.swagger.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class EmployeeEditUserDTO {

    @Nullable
    @JsonProperty("firstName")
    private String firstName;
    @Nullable
    private String lastName;
    @Nullable
    private String email;
    @Nullable
    private String phoneNumber;
    @Nullable
    private AddressPutDTO address;

    @JsonIgnore
    private List<BankAccount> bankAccounts;

    @JsonProperty("Limits")
    @Valid
    @Nullable
    private List<Limit> limits;

    public EmployeeEditUserDTO() {
    }

    public EmployeeEditUserDTO firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Schema(example = "James")
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

    @Schema(example = "Ford")
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

    @Schema(example = "james@email.com")
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

    @Schema(example = "0612345678")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EmployeeEditUserDTO address(AddressPutDTO address) {
        this.address = address;
        return this;
    }

    @Valid
    public AddressPutDTO getAddress() {
        return address;
    }

    public void setAddress(AddressPutDTO address) {
        this.address = address;
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

    public void fillEmpty(User src) {
        if(firstName == null) {
            firstName = src.getFirstName();
        }

        if(lastName == null) {
            lastName = src.getLastName();
        }

        if(phoneNumber == null) {
            phoneNumber = src.getPhoneNumber();
        }

        if(email == null) {
            email = src.getEmail();
        }

        if(address == null) {
            address = new AddressPutDTO();
            address.fillEmpty(src.getAddress());
        }
    }

    public Address getAddressObj(Address oldAddress) {
        return oldAddress.city(address.getCity()).country(address.getCountry()).houseNumber(address.getHouseNumber())
                .postalcode(address.getPostalcode()).street(address.getStreet());
    }
}
