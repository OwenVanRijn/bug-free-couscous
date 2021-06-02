package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.exceptions.BadRequestException;
import io.swagger.exceptions.RestException;
import io.swagger.model.Transaction;
import io.swagger.model.Limit;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BankAccount
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")

@Entity
public class BankAccount   {
  @JsonProperty("name")
  private String name = null;

  @Id
  @GeneratedValue
  @JsonProperty("id")
  private Integer id = null;

  public enum AccountTypeEnum {
    CURRENT("Current"),
    
    SAVINGS("Savings");

    private String value;

    AccountTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AccountTypeEnum fromValue(String text) {
      for (AccountTypeEnum b : AccountTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("accountType")
  private AccountTypeEnum accountType = null;

  @JsonProperty("iban")
  private String IBAN = null;

  @JsonProperty("amount")
  private Long amount = null;

  @OneToOne
  @Valid
  @Nullable
  private Limit balanceMin = null;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_id")
  @Nullable
  private User owner = null;

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public BankAccount name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(example = "Daily Account", required = true, description = "")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BankAccount id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
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

  public BankAccount accountType(AccountTypeEnum accountType) {
    this.accountType = accountType;
    return this;
  }

  /**
   * Get accountType
   * @return accountType
   **/
  @Schema(example = "Current", required = true, description = "")
      @NotNull

    public AccountTypeEnum getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountTypeEnum accountType) {
    this.accountType = accountType;
  }

  public BankAccount IBAN(String IBAN) {
    this.IBAN = IBAN;
    return this;
  }

  /**
   * Get IBAN
   * @return IBAN
   **/
  @Schema(example = "NL20RABO124235346", required = true, description = "")
      @NotNull

  public String getIBAN() {
    return IBAN;
  }

  public void setIBAN(String IBAN) {
    this.IBAN = IBAN;
  }

  public BankAccount amount(Long amount) {
    this.amount = amount;
    return this;
  }

  public BankAccount amount(Double amount) {
    Double a = amount * 100;
    this.amount = a.longValue();
    return this;
  }
  /**
   * Get amount
   * @return amount
   **/
  @Schema(example = "1200", required = true, description = "")
      @NotNull

    @Valid
    public Long getAmount() {
    return amount;
  }

    @Valid
    public Double getAmountDecimal() {
      return amount.doubleValue() / 100;
    }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  public Limit getBalanceMin() {
    if (balanceMin == null) {
      return LimitType.BANKACCOUNT_MIN.getDefault();
    }
    return balanceMin;
  }

  public void setBalanceMin(Limit balanceMin) {
    this.balanceMin = balanceMin;
  }

  public BankAccount balanceMin(Limit balanceMin){
    this.balanceMin = balanceMin;
    return this;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankAccount bankAccount = (BankAccount) o;
    return Objects.equals(this.name, bankAccount.name) &&
        Objects.equals(this.id, bankAccount.id) &&
        Objects.equals(this.accountType, bankAccount.accountType) &&
        Objects.equals(this.IBAN, bankAccount.IBAN) &&
        Objects.equals(this.amount, bankAccount.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id, accountType, IBAN, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankAccount {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
    sb.append("    IBAN: ").append(toIndentedString(IBAN)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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

  public void addAmount(Long amount){
    if (amount > 0)
      this.amount += amount.longValue();
  }

  public void removeAmount(Long amount) throws RestException {
    if (amount > 0)
      this.amount -= amount.longValue();

    if (this.amount < balanceMin.getMax())
      throw new BadRequestException("Bank accounts cannot go under the limit");
  }
}
