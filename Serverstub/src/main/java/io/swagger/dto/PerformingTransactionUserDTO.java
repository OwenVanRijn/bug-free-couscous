package io.swagger.dto;

import io.swagger.model.User;

public class PerformingTransactionUserDTO {
    private String name;
    private User.RoleEnum role;

    public PerformingTransactionUserDTO(User user){
        name = user.getFirstName() + " " + user.getLastName();
        role = user.getRole();
    }

    public String getName() {
        return name;
    }

    public User.RoleEnum getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(User.RoleEnum role) {
        this.role = role;
    }
}
