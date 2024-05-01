package com.talxan.ywitter.yuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.talxan.ywitter.constants.Constant.PHOTO_DIRECTORY;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/upload/photo")
    public ResponseEntity<String> uploadPic(@RequestParam("id") Integer id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(userService.uploadPic(id, file));
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<String> follow(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.follow(id));
    }

    @GetMapping("/getFollowing")
    public ResponseEntity<List<UserResponse>> getFollowing() {
        return ResponseEntity.ok(userService.getFollowing());
    }

    @GetMapping("/getFollowers")
    public ResponseEntity<List<UserResponse>> getFollowers() {
        return ResponseEntity.ok(userService.getFollowers());
    }

    @GetMapping("/whoToFollow")
    public ResponseEntity<List<UserResponse>> getWhoToFollow() {return ResponseEntity.ok(userService.getWhoToFollow());}

    @GetMapping("/getUser")
    public ResponseEntity<UserResponse> getUser() {return ResponseEntity.ok(userService.getUser());}

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
