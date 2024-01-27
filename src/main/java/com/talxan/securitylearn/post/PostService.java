package com.talxan.securitylearn.post;

import com.talxan.securitylearn.user.User;
import com.talxan.securitylearn.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.talxan.securitylearn.post.Post;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Post createPost(PostRequest request) {
        User postUser = (com.talxan.securitylearn.user.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //User postUser = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var post = Post.builder()
                .content(request.getContent())
                .postUser(postUser)
                .postedAt(new Date())
                .build();

        return postRepository.save(post);
    }
}
