package com.talxan.ywitter.admin;

import com.talxan.ywitter.mappers.UserMapper;
import com.talxan.ywitter.yuser.UserRepository;
import com.talxan.ywitter.yuser.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    @Transactional
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toUserResponse).collect(Collectors.toList());
    }
}
