package com.msa.userservice.controller;

import com.msa.userservice.dto.UserDTO;
import com.msa.userservice.mapper.HttpMapper;
import com.msa.userservice.service.UserService;
import com.msa.userservice.vo.request.RequestUser;
import com.msa.userservice.vo.response.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/user-service") API Gateway 에서 pattern 을 변경해 줌으로서 필요 없어짐
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Environment environment;
    private final HttpMapper httpMapper;

    @GetMapping("/health_check")
    public String status() {
        return "It's working in user-service " +
                "\n LOCAL SEVER PORT >> " + environment.getProperty("local.server.port") +
                "\n SEVER PORT >> " + environment.getProperty("server.port") +
                "\n TOKEN SECRET-KEY >> " + environment.getProperty("token.secret-key") +
                "\n TOKEN EXPIRATION-TIME >> " + environment.getProperty("token.expiration-time");
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser) {

        UserDTO userDTO = httpMapper.toDTO(requestUser);
        userDTO = userService.createUser(userDTO);

        ResponseUser responseUser = httpMapper.toResponse(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserDTO> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();

        userList.forEach(dto -> result.add(httpMapper.toResponse(dto)));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDTO userDTO = userService.getUserByUserId(userId);

        ResponseUser result = httpMapper.toResponse(userDTO);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
