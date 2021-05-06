package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Body
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")


public class Body   {
  @JsonProperty("IBAN_from")
  private String ibANFrom = null;

  @JsonProperty("IBAN_to")
  private String ibANTo = null;

  @JsonProperty("amount")
  private BigDecimal amount = null;

  public Body ibANFrom(String ibANFrom) {
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

  public Body ibANTo(String ibANTo) {
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

  public Body amount(BigDecimal amount) {
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
    Body body = (Body) o;
    return Objects.equals(this.ibANFrom, body.ibANFrom) &&
        Objects.equals(this.ibANTo, body.ibANTo) &&
        Objects.equals(this.amount, body.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ibANFrom, ibANTo, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body {\n");
    
    sb.append("    ibANFrom: ").append(toIndentedString(ibANFrom)).append("\n");
    sb.append("    ibANTo: ").append(toIndentedString(ibANTo)).append("\n");
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
