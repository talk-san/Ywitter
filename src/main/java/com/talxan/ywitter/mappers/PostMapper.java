package com.talxan.ywitter.mappers;

import com.talxan.ywitter.post.Post;
import com.talxan.ywitter.post.PostResponse;
import com.talxan.ywitter.yuser.User;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

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
                .userId(postUser.getYuserId())
                .userPhoto(postUser.getPhotoUrl())
                .parentPostId(Optional.ofNullable(post.getParentPost()).map(Post::getPostId).orElse(0))
                .numOfComments(Optional.ofNullable(post.getComments()).map(List::size).orElse(0)) // TODO Implement this
                .numOfLikes(post.getLikes().size())
                .build();
    }
}
