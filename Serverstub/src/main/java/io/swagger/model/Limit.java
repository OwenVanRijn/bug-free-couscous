package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
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

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("limit")
  private BigDecimal max = null;

  @JsonProperty("current")
  private BigDecimal current = null;

  public Limit name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(example = "AbsoluteLimit", required = true, description = "")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Limit limit(BigDecimal limit) {
    this.max = limit;
    return this;
  }

  /**
   * Get limit
   * @return limit
   **/
  @Schema(example = "1000", required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getMax() {
    return max;
  }

  public void setMax(BigDecimal limit) {
    this.max = limit;
  }

  public Limit current(BigDecimal current) {
    this.current = current;
    return this;
  }

  /**
   * Get current
   * @return current
   **/
  @Schema(example = "0", required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getCurrent() {
    return current;
  }

  public void setCurrent(BigDecimal current) {
    this.current = current;
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
    return Objects.equals(this.name, limit.name) &&
        Objects.equals(this.max, limit.max) &&
        Objects.equals(this.current, limit.current);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, max, current);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Limit {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    limit: ").append(toIndentedString(max)).append("\n");
    sb.append("    current: ").append(toIndentedString(current)).append("\n");
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
