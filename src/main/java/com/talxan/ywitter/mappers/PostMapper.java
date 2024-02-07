package com.talxan.ywitter.mappers;

import com.talxan.ywitter.post.Post;
import com.talxan.ywitter.post.PostResponse;
import com.talxan.ywitter.yuser.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostMapper {
    public static PostResponse mapToPostResponse(Post post) {
        User postUser = post.getPostYuser();
        return PostResponse.builder()
                .contents(post.getContent())
                .username(postUser.getUsername())
                .firstName(postUser.getFirstName())
                .postedAt(post.getPostedAt())
                .build();
    }
}
