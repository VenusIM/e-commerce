package com.msa.userservice.service;

import com.msa.userservice.dto.UserDTO;
import com.msa.userservice.entity.UserEntity;
import com.msa.userservice.mapper.UserMapper;
import com.msa.userservice.repository.UserRepository;
import com.msa.userservice.vo.response.ResponseOrder;
import com.msa.userservice.vo.response.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Environment environment;

    // Bean 등록
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setUserId(UUID.randomUUID().toString());

        UserEntity userEntity = userMapper.toEntity(userDTO);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        userEntity = userRepository.save(userEntity);
        userDTO = userMapper.toDTO(userEntity);

        return userDTO;
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        UserDTO userDTO =  userMapper.toDTO(userEntity);
        if(userEntity == null) {
            throw new UsernameNotFoundException("User is not found");
        }

//        List<ResponseOrder> orders = new ArrayList<>();

        /* Using as rest template */
        String orderUrl = String.format(environment.getProperty("order-service.url"),userId);
        ResponseEntity<List<ResponseOrder>> orderListResponse =
                restTemplate.exchange(orderUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<ResponseOrder>>() {
                });

        List<ResponseOrder> orders = orderListResponse.getBody();
        userDTO.setOrders(orders);
        return userDTO;
    }

    @Override
    public Iterable<UserDTO> getUserByAll() {
        Iterable<UserEntity> userList = userRepository.findAll();

        List<UserDTO> result = new ArrayList<>();
        userList.forEach(entity -> result.add(userMapper.toDTO(entity)));
        return result;
    }

    @Override
    public UserDTO getUserDetailByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        return userMapper.toDTO(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        return new User(userEntity.getEmail(),
                userEntity.getEncryptedPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>());
    }
}
