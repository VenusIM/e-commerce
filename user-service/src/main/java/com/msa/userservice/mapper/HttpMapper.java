package com.msa.userservice.mapper;

import com.msa.userservice.dto.UserDTO;
import com.msa.userservice.vo.request.RequestLogin;
import com.msa.userservice.vo.request.RequestUser;
import com.msa.userservice.vo.response.ResponseUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface HttpMapper {
    UserDTO toDTO(RequestUser requestUser);
    ResponseUser toResponse(UserDTO userDTO);
}
