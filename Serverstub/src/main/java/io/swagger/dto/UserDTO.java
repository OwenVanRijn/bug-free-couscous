package io.swagger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.Address;
import io.swagger.model.BankAccount;
import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Address address;
    private List<Role> roles;
    private List<BankaccountDTO> bankAccounts;

    public UserDTO(User user) {
        id = user.getId();
        username = user.getUsername();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        phoneNumber = user.getPhoneNumber();
        address = user.getAddress();
        roles = user.getRole();
        bankAccounts = user.getBankAccounts().stream().map(BankaccountDTO::new).collect(Collectors.toList());
    }

    public UserDTO() {}

    @Schema(example = "1")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Schema(example = "james123")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Schema(example = "James")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Schema(example = "Dean")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Schema(example = "jamesdean@mail.com")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Schema(example = "0612345678")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<BankaccountDTO> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankaccountDTO> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ", roles=" + roles +
                '}';
    }
}
