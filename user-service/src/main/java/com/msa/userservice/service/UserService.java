package com.msa.userservice.service;

import com.msa.userservice.dto.UserDTO;
import com.msa.userservice.entity.UserEntity;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();
}
