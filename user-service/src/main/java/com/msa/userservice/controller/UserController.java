package com.msa.userservice.controller;

import com.msa.userservice.dto.UserDTO;
import com.msa.userservice.service.UserService;
import com.msa.userservice.vo.RequestUser;
import com.msa.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("health_check")
    public String status() {
        return "It's working in user-service";
    }

    @PostMapping("users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDTO userDTO = modelMapper.map(requestUser, UserDTO.class);
        userService.createUser(userDTO);

        ResponseUser responseUser = modelMapper.map(userDTO, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

}
