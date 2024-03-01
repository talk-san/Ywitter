package com.talxan.ywitter.mappers;

import com.talxan.ywitter.yuser.User;
import com.talxan.ywitter.yuser.UserResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserMapper {
    public static UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .id(user.getYuserId())
                .build();
    }
}
