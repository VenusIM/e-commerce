package com.msa.userservice.service;

import com.msa.userservice.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
}
