package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CreateBankaccountSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")


public class CreateBankaccountSchema   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("id")
  private Integer id = null;

  /**
   * Gets or Sets accountType
   */
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

  @JsonProperty("IBAN")
  private String IBAN = null;

  @JsonProperty("amount")
  private Long amount = null;

  public CreateBankaccountSchema name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(example = "Daily Account", description = "")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CreateBankaccountSchema id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(example = "1", description = "")
  
    public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public CreateBankaccountSchema accountType(AccountTypeEnum accountType) {
    this.accountType = accountType;
    return this;
  }

  /**
   * Get accountType
   * @return accountType
   **/
  @Schema(example = "Current", description = "")
  
    public AccountTypeEnum getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountTypeEnum accountType) {
    this.accountType = accountType;
  }

  public CreateBankaccountSchema IBAN(String IBAN) {
    this.IBAN = IBAN;
    return this;
  }

  /**
   * Get IBAN
   * @return IBAN
   **/
  @Schema(example = "NL20RABO124235346", description = "")
  
    public String getIBAN() {
    return IBAN;
  }

  public void setIBAN(String IBAN) {
    this.IBAN = IBAN;
  }

  public CreateBankaccountSchema amount(Long amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(example = "1200", description = "")
  
    @Valid
    public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateBankaccountSchema createBankaccountSchema = (CreateBankaccountSchema) o;
    return Objects.equals(this.name, createBankaccountSchema.name) &&
        Objects.equals(this.id, createBankaccountSchema.id) &&
        Objects.equals(this.accountType, createBankaccountSchema.accountType) &&
        Objects.equals(this.IBAN, createBankaccountSchema.IBAN) &&
        Objects.equals(this.amount, createBankaccountSchema.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id, accountType, IBAN, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateBankaccountSchema {\n");
    
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
}
