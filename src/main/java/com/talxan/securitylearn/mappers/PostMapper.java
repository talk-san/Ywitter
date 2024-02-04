package com.talxan.securitylearn.mappers;

import com.talxan.securitylearn.post.Post;
import com.talxan.securitylearn.post.PostResponse;
import com.talxan.securitylearn.user.User;
import com.talxan.securitylearn.user.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostMapper {
    public static PostResponse mapToPostResponse(Post post) {
        User postUser = post.getPostUser();
        return PostResponse.builder()
                .contents(post.getContent())
                .username(postUser.getUsername())
                .firstName(postUser.getFirstName())
                .postedAt(post.getPostedAt())
                .build();
    }
}
