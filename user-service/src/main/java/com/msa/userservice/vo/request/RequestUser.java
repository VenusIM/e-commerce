package com.msa.userservice.vo.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RequestUser {

    @NotBlank(message = "Email cannot be null")
    @Min(value = 2, message = "Email not be less than two characters")
    private String email;

    @NotBlank(message = "Name cannot be null")
    @Min(value = 2, message = "Name not be less than two characters")
    private String name;

    @NotBlank(message = "Password cannot be null")
    @Min(value = 8, message = "Password must be equal or gather then eight characters")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
