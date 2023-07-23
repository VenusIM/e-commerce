package com.msa.userservice.controller;

import com.msa.userservice.dto.UserDTO;
import com.msa.userservice.service.UserService;
import com.msa.userservice.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser requestUser) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = modelMapper.map(requestUser, UserDTO.class);
        userService.createUser(userDTO);
        return "create user method is called";
    }

}
