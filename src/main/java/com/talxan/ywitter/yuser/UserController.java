package com.talxan.ywitter.yuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") Integer id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(userService.uploadPhoto(id, file));
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<String> follow(@PathVariable Integer id) {
        return userService.follow(id);
    }

    @GetMapping("/getFollowing")
    public ResponseEntity<List<UserResponse>> getFollowing() {
        List<UserResponse> following = userService.getFollowing();
        return ResponseEntity.ok(following);
    }

    @GetMapping("/getFollowers")
    public ResponseEntity<List<UserResponse>> getFollowers() {
        List<UserResponse> followers = userService.getFollowers();
        return ResponseEntity.ok(followers);
    }

}
