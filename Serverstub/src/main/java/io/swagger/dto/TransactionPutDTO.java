package io.swagger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.exceptions.BadRequestException;
import io.swagger.exceptions.RestException;
import io.swagger.exceptions.ServerErrorException;
import io.swagger.model.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Body
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")

// TODO: figure out inheritance for this?
public class TransactionPutDTO {
  @JsonProperty("IBAN_from")
  @Nullable
  protected String ibanFrom = null;

  @JsonProperty("IBAN_to")
  @Nullable
  protected String ibanTo = null;

  @JsonProperty("amount")
  @Nullable
  protected Double amount = null;

  public TransactionPutDTO ibANFrom(String ibANFrom) {
    this.ibanFrom = ibANFrom;
    return this;
  }

  /**
   * Get ibANFrom
   * @return ibANFrom
   **/
  @Schema(example = "IBAN01", description = "")

    public String getIbanFrom() {
    return ibanFrom;
  }

  public void setIbanFrom(String ibanFrom) {
    this.ibanFrom = ibanFrom;
  }

  public TransactionPutDTO ibANTo(String ibANTo) {
    this.ibanTo = ibANTo;
    return this;
  }

  /**
   * Get ibANTo
   * @return ibANTo
   **/
  @Schema(example = "IBAN02", description = "")

    public String getIbanTo() {
    return ibanTo;
  }

  public void setIbanTo(String ibanTo) {
    this.ibanTo = ibanTo;
  }

  public TransactionPutDTO amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(example = "10.5", description = "")

    @Valid
    public Double getAmount() {
    return amount;
  }

    public Long getAmountLong() {
      Double a = amount * 100;
      return a.longValue();
    }

  public void setAmount(Double amount) {
    this.amount = amount;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionPutDTO body = (TransactionPutDTO) o;
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  public TransactionPostDTO toPostDto() throws RestException {
    if (amount == null || ibanFrom == null || ibanTo == null)
      throw new ServerErrorException("PostDTO is incomplete");

    return new TransactionPostDTO().amount(amount).ibANFrom(ibanFrom).ibANTo(ibanTo);
  }

  public void fillEmpty(Transaction src){
    if (amount == null)
      amount = src.getAmountAsDecimal();

    if (ibanFrom == null)
      ibanFrom = src.getIbanFrom();

    if (ibanTo == null)
      ibanTo = src.getIbanTo();
  }
}
