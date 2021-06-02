package io.swagger.dto;

import io.swagger.model.User;

import java.util.List;

public class UsersPageDTO {

    private Long totalCount;
    private Integer totalPages;
    private List<UserDTO> users;

    public UsersPageDTO(Long totalCount, Integer totalPages, List<UserDTO> users) {
        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.users = users;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
