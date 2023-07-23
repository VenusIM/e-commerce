package com.msa.userservice.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
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

}
