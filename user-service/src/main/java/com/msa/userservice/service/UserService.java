package com.msa.userservice.service;

import com.msa.userservice.dto.UserDTO;
import com.msa.userservice.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserByUserId(String userId);

    Iterable<UserDTO> getUserByAll();
}
