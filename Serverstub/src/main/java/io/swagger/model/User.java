package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.dto.TransactionDTO;
import io.swagger.dto.UserDTO;
import io.swagger.model.Address;
import io.swagger.model.BankAccount;
import io.swagger.model.Limit;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")

@Entity
@Table(name = "user")
public class User {
    @JsonProperty("id")
    @Id
    @GeneratedValue
    private Integer id = null;

    private String username;

    private String password;

    @JsonProperty("FirstName")
    private String firstName = null;

    @JsonProperty("LastName")
    private String lastName = null;

    @JsonProperty("Email")
    private String email = null;

    @JsonProperty("PhoneNumber")
    private String phoneNumber = null;

    @JsonProperty("Address")
    @ManyToOne()
    private Address address = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User username(String username){
        this.username = username;
        return this;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Gets or Sets role
     */

    @JsonProperty("Role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

    @JsonProperty("BankAccounts")
    @Valid
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<BankAccount> bankAccounts = new ArrayList<BankAccount>();

    @OneToOne
    @Valid
    @Nullable
    private Limit dailyLimit = null;

    @OneToOne
    @Valid
    @Nullable
    private Limit globalLimit = null;

    public User id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @Schema(example = "1", required = true, description = "")
    @NotNull

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Get firstName
     *
     * @return firstName
     **/
    @Schema(example = "James", required = true, description = "")
    @NotNull

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Get lastName
     *
     * @return lastName
     **/
    @Schema(example = "Ford", required = true, description = "")
    @NotNull

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Get email
     *
     * @return email
     **/
    @Schema(example = "james@email.com", required = true, description = "")
    @NotNull

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Get phoneNumber
     *
     * @return phoneNumber
     **/
    @Schema(example = "+31 6 12345678", required = true, description = "")
    @NotNull

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User address(Address address) {
        this.address = address;
        return this;
    }

    /**
     * Get address
     *
     * @return address
     **/
    @Schema(required = true, description = "")
    @NotNull

    @Valid
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User role(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    /**
     * Get role
     *
     * @return role
     **/
    @Schema(example = "Customer", required = true, description = "")
    @NotNull

    public List<Role> getRole() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    public User bankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
        return this;
    }

    public User addBankAccountsItem(BankAccount bankAccountsItem) {
        bankAccountsItem.setOwner(this);
        this.bankAccounts.add(bankAccountsItem);
        return this;
    }

    /**
     * Get bankAccounts
     *
     * @return bankAccounts
     **/
    @Schema(required = true, description = "")
    @NotNull
    @Valid
    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    @Nullable
    public Limit getDailyLimit() {
        if (dailyLimit == null){
            return LimitType.CUSTOMER_DAILY_TRANSACTIONS.getDefault();
        }
        return dailyLimit;
    }

    public void setDailyLimit(@Nullable Limit dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    @Nullable
    public Limit getGlobalLimit() {
        if (globalLimit == null){
            return LimitType.CUSTOMER_TRANSACTIONS.getDefault();
        }
        return globalLimit;
    }

    public void setGlobalLimit(@Nullable Limit globalLimit) {
        this.globalLimit = globalLimit;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(this.id, user.id) &&
                Objects.equals(this.firstName, user.firstName) &&
                Objects.equals(this.lastName, user.lastName) &&
                Objects.equals(this.email, user.email) &&
                Objects.equals(this.phoneNumber, user.phoneNumber) &&
                Objects.equals(this.address, user.address) &&
                Objects.equals(this.roles, user.roles) &&
                Objects.equals(this.bankAccounts, user.bankAccounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phoneNumber, address, roles, bankAccounts);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class User {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
        sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
        sb.append("    role: ").append(toIndentedString(roles)).append("\n");
        sb.append("    bankAccounts: ").append(toIndentedString(bankAccounts)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    public UserDTO toUserDTO() {
        return new UserDTO(this);
    }
}
