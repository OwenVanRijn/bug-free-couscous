package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Limit
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")


@Entity
@Table(name = "limitTable")
public class Limit {
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("limit")
  private Long max = null;

  @JsonProperty("type")
  private LimitType type;

  /**
   * Get limit
   * @return limit
   **/
  @Schema(example = "1000", required = true, description = "")
      @NotNull

    @Valid
    public Long getMax() {
    return max;
  }

  public void setMax(Long limit) {
    this.max = limit;
  }

  public Limit limit(Double limit) {
    Double a = limit * 100;
    this.max = a.longValue();
    return this;
  }

  public Limit limit(Long limit){
    this.max = limit;
    return this;
  }

  public LimitType getType() {
    return type;
  }

  public void setType(LimitType type) {
    this.type = type;
  }

  public Limit type(LimitType type){
    this.type = type;
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
    Limit limit = (Limit) o;
    return
        Objects.equals(this.max, limit.max);
  }

  @Override
  public int hashCode() {
    return Objects.hash(max);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Limit {\n");

    sb.append("    limit: ").append(toIndentedString(max)).append("\n");
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
