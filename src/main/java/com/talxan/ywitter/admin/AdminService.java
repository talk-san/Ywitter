package com.talxan.ywitter.admin;

import com.talxan.ywitter.exceptions.PostNotFoundException;
import com.talxan.ywitter.exceptions.UserNotFoundException;
import com.talxan.ywitter.mappers.UserMapper;
import com.talxan.ywitter.post.Post;
import com.talxan.ywitter.post.PostRepository;
import com.talxan.ywitter.yuser.User;
import com.talxan.ywitter.yuser.UserRepository;
import com.talxan.ywitter.yuser.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::mapToUserResponse).collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<String> deleteUserById(Integer id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
        return ResponseEntity.ok("User with id " + id + "successfully deleted");
    }

    @Transactional
    public ResponseEntity<String> deletePostById(Integer id) {
        Post postToDelete = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        postRepository.delete(postToDelete);
        return ResponseEntity.ok("Post with id " + id + " successfully deleted");
    }
}
