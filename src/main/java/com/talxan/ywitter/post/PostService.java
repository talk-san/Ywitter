package com.talxan.ywitter.post;

import com.talxan.ywitter.exceptions.PostNotFoundException;
import com.talxan.ywitter.like.Like;
import com.talxan.ywitter.like.LikeRepository;
import com.talxan.ywitter.mappers.PostMapper;
import com.talxan.ywitter.mappers.UserMapper;
import com.talxan.ywitter.yuser.User;
import com.talxan.ywitter.yuser.UserResponse;
import com.talxan.ywitter.yuser.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.talxan.ywitter.mappers.PostMapper.mapToPostResponse;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
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
        return mapToPostResponse(post);
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
        parentPost.getComments().add(post); // Also keeping comments separate to avoid database hits?
        postRepository.save(post);
        return PostMapper.mapToPostResponse(post);
    }

    @Transactional
    public String like(Integer postId) throws BadRequestException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post not found with id" + postId));
        Optional<Like> likeOptional = likeRepository.findByLikedPost(post);
        User user = userService.getCurrentUser();

        if (likeOptional.isPresent()) {
            Like like = likeOptional.get();
            user.getLikedPosts().remove(like);
            post.getLikes().remove(like);
            likeRepository.deleteByPostIdAndUserId(post, user);
            return "Like with id " + like.getLikeId() + "removed from post with id " + like.getLikedPost().getPostId();
        } else {
            Like newLike = Like.builder()
                    .yuser(user)
                    .likedPost(post)
                    .build();
            user.getLikedPosts().add(newLike);
            post.getLikes().remove(newLike);
            likeRepository.save(newLike);
            return "Like with id" + newLike.getLikeId() + "added to post " + post.getPostId();
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

    public List<UserResponse> getLikes(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return post.getLikes().stream().map(l -> UserMapper.mapToUserResponse(l.getYuser())).collect(Collectors.toList());
    }
}
