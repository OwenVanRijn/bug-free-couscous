package io.swagger.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.exceptions.RestException;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Body
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")


public class TransactionPostDTO {
  @JsonProperty("IBAN_from")
  protected String ibanFrom = null;

  @JsonProperty("IBAN_to")
  protected String ibanTo = null;

  @JsonProperty("amount")
  protected Double amount = null;

  public TransactionPostDTO ibANFrom(String ibANFrom) {
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

  public void setIbanFrom(String ibanFrom) {
    this.ibanFrom = ibanFrom;
  }

  public TransactionPostDTO ibANTo(String ibANTo) {
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

  public void setIbanTo(String ibanTo) {
    this.ibanTo = ibanTo;
  }

  public TransactionPostDTO amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(example = "10.5", required = true, description = "")
      @NotNull

    @Valid
    public Double getAmount() {
    return amount;
  }

    public Long getAmountLong() {
      if (amount == null)
        return 0L; // who needs error handling anyway

      Double a = amount * 100;
      return a.longValue();
    }

  public void setAmount(Double amount) {
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
    TransactionPostDTO body = (TransactionPostDTO) o;
    return Objects.equals(this.ibanFrom, body.ibanFrom) &&
        Objects.equals(this.ibanTo, body.ibanTo) &&
        Objects.equals(this.amount, body.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ibanFrom, ibanTo, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body {\n");
    
    sb.append("    ibANFrom: ").append(toIndentedString(ibanFrom)).append("\n");
    sb.append("    ibANTo: ").append(toIndentedString(ibanTo)).append("\n");
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

  public Transaction toTransaction(User performingUser) throws RestException {
    Transaction t = new Transaction();
    t.type(Transaction.TypeEnum.TRANSACTION)
            .ibANFrom(ibanFrom)
            .ibANTo(ibanTo)
            .amount(getAmountLong())
            .performedBy(performingUser);

    t.validate();
    return t;
  }
}
