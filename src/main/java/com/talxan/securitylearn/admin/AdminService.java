package com.talxan.securitylearn.admin;

import com.talxan.securitylearn.mappers.UserMapper;
import com.talxan.securitylearn.user.UserRepository;
import com.talxan.securitylearn.user.UserResponse;
import com.talxan.securitylearn.user.UserService;
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
