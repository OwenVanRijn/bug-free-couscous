package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.TransactionPerformedBy;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")


public class Transaction   {
  @JsonProperty("id")
  private Integer id = null;

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
  private OffsetDateTime timestamp = null;

  @JsonProperty("IBAN_from")
  private String ibANFrom = null;

  @JsonProperty("IBAN_to")
  private String ibANTo = null;

  @JsonProperty("performed_by")
  private TransactionPerformedBy performedBy = null;

  @JsonProperty("amount")
  private BigDecimal amount = null;

  public Transaction id(Integer id) {
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

  @Min(0)  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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

  public Transaction timestamp(OffsetDateTime timestamp) {
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
    public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public Transaction ibANFrom(String ibANFrom) {
    this.ibANFrom = ibANFrom;
    return this;
  }

  /**
   * Get ibANFrom
   * @return ibANFrom
   **/
  @Schema(example = "IBAN01", required = true, description = "")
      @NotNull

    public String getIbANFrom() {
    return ibANFrom;
  }

  public void setIbANFrom(String ibANFrom) {
    this.ibANFrom = ibANFrom;
  }

  public Transaction ibANTo(String ibANTo) {
    this.ibANTo = ibANTo;
    return this;
  }

  /**
   * Get ibANTo
   * @return ibANTo
   **/
  @Schema(example = "IBAN02", required = true, description = "")
      @NotNull

    public String getIbANTo() {
    return ibANTo;
  }

  public void setIbANTo(String ibANTo) {
    this.ibANTo = ibANTo;
  }

  public Transaction performedBy(TransactionPerformedBy performedBy) {
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
    public TransactionPerformedBy getPerformedBy() {
    return performedBy;
  }

  public void setPerformedBy(TransactionPerformedBy performedBy) {
    this.performedBy = performedBy;
  }

  public Transaction amount(BigDecimal amount) {
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
    public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
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
        Objects.equals(this.ibANFrom, transaction.ibANFrom) &&
        Objects.equals(this.ibANTo, transaction.ibANTo) &&
        Objects.equals(this.performedBy, transaction.performedBy) &&
        Objects.equals(this.amount, transaction.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, timestamp, ibANFrom, ibANTo, performedBy, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    ibANFrom: ").append(toIndentedString(ibANFrom)).append("\n");
    sb.append("    ibANTo: ").append(toIndentedString(ibANTo)).append("\n");
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
}