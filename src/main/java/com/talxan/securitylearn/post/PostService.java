package com.talxan.securitylearn.post;

import com.talxan.securitylearn.exceptions.PostNotFoundException;
import com.talxan.securitylearn.mappers.PostMapper;
import com.talxan.securitylearn.user.User;
import com.talxan.securitylearn.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.talxan.securitylearn.mappers.PostMapper.mapToPostResponse;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public PostResponse createPost(PostRequest request) {
        var post = Post.builder()
                .content(request.getContent())
                .postUser(userService.getCurrentUser())
                .postedAt(new Date())
                .build();
        postRepository.save(post);
        return mapToPostResponse(post);
    }

    @Transactional
    public PostResponse updatePost(Integer id, PostRequest request) {
        Post updatedPost = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        updatedPost.setContent(request.getContent());
        postRepository.save(updatedPost);
        return mapToPostResponse(updatedPost);
    }

    @Transactional
    public void deletePostById(Integer id) {
        Post postToDelete = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        postRepository.delete(postToDelete);
    }

    @Transactional
    public List<PostResponse> getAllPosts() {
        User currentUser = userService.getCurrentUser();
        return currentUser.getPosts().stream().map(PostMapper::mapToPostResponse).collect(Collectors.toList());
    }

    public List<PostResponse> getFeed() {
        List<Integer> ids = userService.getCurrentUser().getFollowing().stream().map(User::getUserId).toList();
        return postRepository.findFirst10ByPostUser_UserIdInOrderByPostedAtDesc(ids).stream().map(PostMapper::mapToPostResponse).collect(Collectors.toList());

    }



}
