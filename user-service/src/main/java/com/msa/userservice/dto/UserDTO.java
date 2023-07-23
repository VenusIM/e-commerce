package com.msa.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String email;
    private String name;
    private String password;
    private String userId;
    private LocalDateTime createdAt;
    private String encryptedPassword;
}
