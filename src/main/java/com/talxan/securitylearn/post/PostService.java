package com.talxan.securitylearn.post;

import com.talxan.securitylearn.exceptions.PostNotFoundException;
import com.talxan.securitylearn.user.User;
import com.talxan.securitylearn.user.UserRepository;
import com.talxan.securitylearn.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public PostResponse createPost(PostRequest request) {
        User postUser = userService.getCurrentUser();

        var post = Post.builder()
                .content(request.getContent())
                .postUser(userService.getCurrentUser())
                .postedAt(new Date())
                .build();

        postRepository.save(post);
        return PostResponse.builder()
                .contents(post.getContent())
                .username(postUser.getUsername())
                .build();
    }

    @Transactional
    public void deletePostById(Integer id) {
        Post postToDelete = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        postRepository.delete(postToDelete);
    }

    @Transactional
    public List<PostResponse> getAllPosts() {
        return userService.getCurrentUser().getPosts().stream().map(this::mapToPostResponse).collect(Collectors.toList());
    }

    private PostResponse  mapToPostResponse(Post post) {
        User postUser = userService.getCurrentUser();
        return PostResponse.builder()
                .contents(post.getContent())
                .username(postUser.getUsername())
                .firstName(postUser.getFirstName())
                .postedAt(post.getPostedAt())
                .build();
    }


}
