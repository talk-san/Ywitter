package com.talxan.ywitter.admin;

import com.talxan.ywitter.yuser.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<UserResponse>> getAllUsers () {
        List<UserResponse> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/post")
    @PreAuthorize("hasAuthority('admin:create')")
    public String post() {
        return "POST:: admin controller";
    }
    @PutMapping("/put")
    @PreAuthorize("hasAuthority('admin:update')")
    public String put() {
        return "PUT:: admin controller";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('admin:delete')")
    public String delete() {
        return "DELETE:: admin controller";
    }

    @DeleteMapping("/deleteUserById")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer id) {
        return adminService.deleteUserById(id);
    }

    @DeleteMapping("/deletePostById")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<String> deletePostById(@PathVariable Integer id) {
        return adminService.deletePostById(id);
    }

}
