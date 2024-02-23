package com.talxan.ywitter.post;

import com.talxan.ywitter.exceptions.PostNotFoundException;
import com.talxan.ywitter.mappers.PostMapper;
import com.talxan.ywitter.post.like.Like;
import com.talxan.ywitter.post.like.LikeRepository;
import com.talxan.ywitter.yuser.User;
import com.talxan.ywitter.yuser.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.talxan.ywitter.mappers.PostMapper.mapToPostResponse;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserService userService;

    @Transactional
    public PostResponse get(Integer id) {
        return mapToPostResponse(postRepository.findById(id).orElseThrow(PostNotFoundException::new));
    }

    @Transactional
    public PostResponse create(PostRequest request) {
        var post = Post.builder()
                .text(request.getText())
                .postYuser(userService.getCurrentUser())
                .parentPost(null) // No parent post since this is not a comment,
                .postedAt(new Date())
                .build();
        postRepository.save(post);
        return mapToPostResponse(post); // Also why am I even mapping ?
    }

    @Transactional
    public PostResponse comment(Integer parentPostId, PostRequest request) {
        var parentPost = postRepository.findById(parentPostId).orElseThrow(PostNotFoundException::new);
        var post = Post.builder()
                .text(request.getText())
                .postYuser(userService.getCurrentUser())
                .postedAt(new Date())
                .parentPost(parentPost)
                .build();
        parentPost.getComments().add(post);
        postRepository.save(post);
        return PostMapper.mapToPostResponse(post);
    }

    @Transactional
    public void like(Integer postToLike) throws BadRequestException {
        var post = postRepository.findById(postToLike).orElseThrow(PostNotFoundException::new);
        var user = userService.getCurrentUser();
        var like = Like.builder().likeUser(user).likePost(post).build();

        if (post.getLikes().stream().anyMatch(l -> l.getLikeUser().equals(user))) { //TODO This does not work, fix
            throw new BadRequestException(); // don't return ResponseEntity here, only in controller
        } else {
            likeRepository.save(like);
        }
    }

    @Transactional
    public PostResponse updatePost(Integer id, PostRequest request) {
        Post updatedPost = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        updatedPost.setText(request.getText());
        postRepository.save(updatedPost);
        return mapToPostResponse(updatedPost);
    }

    @Transactional
    public void deletePostById(Integer id) {
        Post postToDelete = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        // Check if post belongs to user;
        if (!Objects.equals(postToDelete.getPostYuser().getYuserId(), userService.getCurrentUser().getYuserId())) {
            throw new IllegalStateException("Post does not belong to user");
        } else {
            postRepository.delete(postToDelete);
        }

    }

    @Transactional
    public List<PostResponse> getAllPosts() {
        User currentUser = userService.getCurrentUser();
        return currentUser.getPosts().stream().map(PostMapper::mapToPostResponse).collect(Collectors.toList());
    }

    public List<PostResponse> getFeed() {
        List<Integer> ids = userService.getCurrentUser().getFollowing().stream().map(User::getYuserId).toList();
        return postRepository.findFirst10ByPostYuser_YuserIdInOrderByPostedAtDesc(ids).stream().map(PostMapper::mapToPostResponse).collect(Collectors.toList());

    }

}
