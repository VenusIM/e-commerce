package com.msa.userservice.dto;

import com.msa.userservice.vo.response.ResponseUser;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class UserDTO implements Serializable {
    private String email;
    private String name;
    private String userId;
    private String encryptedPassword;

    private String password;
    private LocalDateTime createdAt;
    private List<ResponseUser> orders;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ResponseUser> getOrders() {
        return orders;
    }

    public void setOrders(List<ResponseUser> orders) {
        this.orders = orders;
    }
}
