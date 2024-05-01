package com.talxan.ywitter.post;

import com.talxan.ywitter.yuser.UserResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/get/{id}")
    public ResponseEntity<PostResponse> get(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.get(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PostResponse> create(@RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.create(request));
    }

    @PostMapping("/comment/{id}")
    public ResponseEntity<PostResponse> comment(@PathVariable("id") Integer parentPostId, @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.comment(parentPostId, request));
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<String> like(@PathVariable("id") Integer postId) throws BadRequestException {
        return ResponseEntity.ok(postService.like(postId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Integer id, @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Integer id) {
        postService.deletePostById(id);
        return ResponseEntity.ok("Post deleted");
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/all/parent")
    public ResponseEntity<List<PostResponse>> getAllParentPosts() {
        List<PostResponse> posts = postService.getAllParentPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostResponse>> getFeed() {
        return ResponseEntity.ok(postService.getFeed());
    }

    @GetMapping("/getLikes/{id}")
    public ResponseEntity<List<UserResponse>> getLikes(@PathVariable Integer id) {return ResponseEntity.ok(postService.getLikes(id));}

    @GetMapping("/getComments/{id}")
    public ResponseEntity<List<PostResponse>> getComments(@PathVariable Integer id) {return ResponseEntity.ok(postService.getComments(id));}
}
