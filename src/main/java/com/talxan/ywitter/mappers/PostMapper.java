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
                .postId(post.getPostId())
                .text(post.getText())
                .username(postUser.getUsername())
                .firstName(postUser.getFirstName())
                .postedAt(post.getPostedAt())
                .parentPost(post.getParentPost())
                .build();
    }
}
