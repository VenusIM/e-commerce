package com.msa.userservice.dto;

import com.msa.userservice.vo.response.ResponseUser;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private String email;
    private String name;
    private String password;
    private String userId;
    private LocalDateTime createdAt;
    private String encryptedPassword;

    private List<ResponseUser> orders;

    public void setOrders(List<ResponseUser> orders) {
        this.orders = orders;
    }
}
