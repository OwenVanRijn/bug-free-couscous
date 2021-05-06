package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TransactionGet
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")


public class TransactionGet   {
  @JsonProperty("Transactions")
  @Valid
  private List<Transaction> transactions = new ArrayList<Transaction>();

  @JsonProperty("Count")
  private Integer count = null;

  @JsonProperty("PageCount")
  private Integer pageCount = null;

  public TransactionGet transactions(List<Transaction> transactions) {
    this.transactions = transactions;
    return this;
  }

  public TransactionGet addTransactionsItem(Transaction transactionsItem) {
    this.transactions.add(transactionsItem);
    return this;
  }

  /**
   * Get transactions
   * @return transactions
   **/
  @Schema(required = true, description = "")
      @NotNull
    @Valid
    public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  public TransactionGet count(Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Get count
   * minimum: 0
   * @return count
   **/
  @Schema(example = "69", required = true, description = "")
      @NotNull

  @Min(0)  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public TransactionGet pageCount(Integer pageCount) {
    this.pageCount = pageCount;
    return this;
  }

  /**
   * Get pageCount
   * minimum: 0
   * @return pageCount
   **/
  @Schema(example = "7", required = true, description = "")
      @NotNull

  @Min(0)  public Integer getPageCount() {
    return pageCount;
  }

  public void setPageCount(Integer pageCount) {
    this.pageCount = pageCount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionGet transactionGet = (TransactionGet) o;
    return Objects.equals(this.transactions, transactionGet.transactions) &&
        Objects.equals(this.count, transactionGet.count) &&
        Objects.equals(this.pageCount, transactionGet.pageCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactions, count, pageCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionGet {\n");
    
    sb.append("    transactions: ").append(toIndentedString(transactions)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    pageCount: ").append(toIndentedString(pageCount)).append("\n");
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
