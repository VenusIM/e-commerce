package com.msa.userservice.mapper;

import com.msa.userservice.dto.UserDTO;
import com.msa.userservice.vo.request.RequestLogin;
import com.msa.userservice.vo.request.RequestUser;
import com.msa.userservice.vo.response.ResponseUser;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface HttpMapper {
    UserDTO toDTO(RequestUser requestUser);
    UserDTO toDTO(RequestLogin requestLogin);
    ResponseUser toResponse(UserDTO userDTO);
}
