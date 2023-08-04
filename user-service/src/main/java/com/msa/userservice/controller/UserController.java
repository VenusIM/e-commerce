package com.msa.userservice.controller;

import com.msa.userservice.dto.UserDTO;
import com.msa.userservice.entity.UserEntity;
import com.msa.userservice.service.UserService;
import com.msa.userservice.vo.request.RequestUser;
import com.msa.userservice.vo.response.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Environment environment;
    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in user-service on PORT %s", environment.getProperty("local.server.port"));
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDTO userDTO = modelMapper.map(requestUser, UserDTO.class);
        userService.createUser(userDTO);

        ResponseUser responseUser = modelMapper.map(userDTO, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();

        ModelMapper modelMapper = new ModelMapper();
        List<ResponseUser> result = new ArrayList<>();

        userList.forEach(entity -> result.add(modelMapper.map(entity, ResponseUser.class)));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDTO userDTO = userService.getUserByUserId(userId);

        ResponseUser result = new ModelMapper().map(userDTO, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
