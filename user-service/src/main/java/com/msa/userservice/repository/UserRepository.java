package com.msa.userservice.repository;

import com.msa.userservice.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDTO, Long> {

}
