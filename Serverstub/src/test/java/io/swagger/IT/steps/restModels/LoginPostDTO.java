package io.swagger.IT.steps.restModels;

public class LoginPostDTO {
    public LoginPostDTO() {}
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginPostDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
