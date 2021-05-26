package io.swagger.dto;

import java.util.List;

public class TransactionsPageDTO {
    private Long totalCount;
    private Integer totalPages;
    private List<TransactionDTO> transactions;

    public TransactionsPageDTO(Long totalCount, Integer totalPages, List<TransactionDTO> transactions) {
        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.transactions = transactions;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}
