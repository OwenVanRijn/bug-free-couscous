package io.swagger.dto;

import io.swagger.model.Role;
import io.swagger.model.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PerformingTransactionUserDTO {
    private String name;
    private List<Role> role;

    public PerformingTransactionUserDTO(User user){
        name = user.getFirstName() + " " + user.getLastName();
        role = user.getRole();
    }

    public String getName() {
        return name;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }
}
