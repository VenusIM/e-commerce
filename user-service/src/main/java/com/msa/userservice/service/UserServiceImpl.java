package com.msa.userservice.service;

import com.msa.userservice.dto.UserDTO;
import com.msa.userservice.entity.UserEntity;
import com.msa.userservice.mapper.UserMapper;
import com.msa.userservice.repository.UserRepository;
import com.msa.userservice.vo.response.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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

        List<ResponseUser> orders = new ArrayList<>();
        userDTO.setOrders(orders);
        return userDTO;
    }

    @Override
    public Iterable<UserDTO> getUserByAll() {
        Iterable<UserEntity> userList = userRepository.findAll();

        List<UserDTO> result = Collections.EMPTY_LIST;
        userList.forEach(entity -> result.add(userMapper.toDTO(entity)));
        return result;
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
                Collections.EMPTY_LIST);
    }
}
