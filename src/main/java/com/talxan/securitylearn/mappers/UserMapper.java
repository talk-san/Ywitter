package com.talxan.securitylearn.mappers;

import com.talxan.securitylearn.user.User;
import com.talxan.securitylearn.user.UserResponse;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .id(user.getUserId())
                .build();
    }
}
