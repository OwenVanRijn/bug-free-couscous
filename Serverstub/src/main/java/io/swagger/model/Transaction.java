package io.swagger.model;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.dto.TransactionDTO;
import io.swagger.exceptions.BadRequestException;
import io.swagger.exceptions.RestException;
import io.swagger.services.IbanHelper;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")

@Entity
public class Transaction   {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Long id = null;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    TRANSACTION("Transaction"),
    
    DEPOSIT("Deposit"),
    
    WITHDRAW("Withdraw");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("type")
  private TypeEnum type = null;

  @JsonProperty("timestamp")
  private Date timestamp = null;

  @JsonProperty("IBAN_from")
  @Nullable
  private String ibanFrom = null;

  @JsonProperty("IBAN_to")
  private String ibanTo = null;

  @JsonProperty("performed_by")
  @ManyToOne(fetch = FetchType.LAZY)
  @Nullable
  private User performedBy = null;

  @JsonProperty("amount")
  private Long amount = null;

  public Transaction id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * minimum: 0
   * @return id
   **/
  @Schema(example = "10", required = true, description = "")
      @NotNull

  @Min(0)  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Transaction type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @Schema(example = "Transaction", required = true, description = "")
      @NotNull

    public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public Transaction timestamp(Date timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
   **/
  @Schema(example = "2015-07-20T15:49:04-07:00", required = true, description = "")
      @NotNull

    @Valid
    public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Transaction ibANFrom(String ibANFrom) {
    this.ibanFrom = ibANFrom;
    return this;
  }

  /**
   * Get ibANFrom
   * @return ibANFrom
   **/
  @Schema(example = "IBAN01", required = true, description = "")
      @NotNull

    public String getIbanFrom() {
    return ibanFrom;
  }

  public void setIbanFrom(String ibANFrom) {
    this.ibanFrom = ibANFrom;
  }

  public Transaction ibANTo(String ibANTo) {
    this.ibanTo = ibANTo;
    return this;
  }

  /**
   * Get ibANTo
   * @return ibANTo
   **/
  @Schema(example = "IBAN02", required = true, description = "")
      @NotNull

    public String getIbanTo() {
    return ibanTo;
  }

  public void setIbanTo(String ibANTo) {
    this.ibanTo = ibANTo;
  }

  public Transaction performedBy(User performedBy) {
    this.performedBy = performedBy;
    return this;
  }

  /**
   * Get performedBy
   * @return performedBy
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public User getPerformedBy() {
    return performedBy;
  }

  public void setPerformedBy(User performedBy) {
    this.performedBy = performedBy;
  }

  public Transaction amount(Long amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(example = "10", required = true, description = "")
      @NotNull

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
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.id, transaction.id) &&
        Objects.equals(this.type, transaction.type) &&
        Objects.equals(this.timestamp, transaction.timestamp) &&
        Objects.equals(this.ibanFrom, transaction.ibanFrom) &&
        Objects.equals(this.ibanTo, transaction.ibanTo) &&
        Objects.equals(this.performedBy, transaction.performedBy) &&
        Objects.equals(this.amount, transaction.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, timestamp, ibanFrom, ibanTo, performedBy, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    ibANFrom: ").append(toIndentedString(ibanFrom)).append("\n");
    sb.append("    ibANTo: ").append(toIndentedString(ibanTo)).append("\n");
    sb.append("    performedBy: ").append(toIndentedString(performedBy)).append("\n");
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

  public TransactionDTO toTransactionDTO(){
    return new TransactionDTO(this);
  }

  public Double getAmountAsDecimal(){
    return amount.doubleValue() / 100;
  }

  public Transaction() {
    timestamp = new Date();
  }

  public Transaction copy(){
    Transaction t = new Transaction();
    t.id(id).type(type).timestamp(timestamp).ibANFrom(ibanFrom).ibANTo(ibanTo).performedBy(performedBy).amount(amount);
    return t;
  }

  public void swapIban(){
    String a = ibanFrom;
    ibanFrom = ibanTo;
    ibanTo = a;
  }

  public void validate() throws RestException {
    if (ibanTo == null || amount == null || performedBy == null || timestamp == null || type == null)
      throw new BadRequestException("Transaction info not set");

    if (ibanFrom == null && type == TypeEnum.TRANSACTION)
      throw new BadRequestException("Iban from not set on transaction");

    if (!IbanHelper.validIban(ibanFrom) || !IbanHelper.validIban(ibanTo))
      throw new BadRequestException("Invalid IBAN");

    if (ibanFrom.equals(ibanTo))
      throw new BadRequestException("Source and Dest cannot be the same");

    if (amount < 0)
      throw new BadRequestException("Invalid amount");
  }


}
