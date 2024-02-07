package com.talxan.ywitter.mappers;

import com.talxan.ywitter.yuser.User;
import com.talxan.ywitter.yuser.UserResponse;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .id(user.getYuserId())
                .build();
    }
}
