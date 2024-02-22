package com.talxan.ywitter.yuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
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
    public ResponseEntity<String> followUser(@PathVariable Integer id) {
        return userService.followUser(id);
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

    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public User addUser(@Payload User user) {
        userService.saveUser(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic")
    public User disconnect(@Payload User user) {
        userService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers() {
        return ResponseEntity.ok(userService.findUsers());
    }

}
