package com.talxan.securitylearn.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public ResponseEntity<List<User>> getFollowing() {
        return userService.getFollowing();
    }
}
