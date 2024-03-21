package com.talxan.ywitter.auth;


import com.talxan.ywitter.yuser.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserRepository repository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            @Valid @RequestBody RegisterRequest request, HttpServletRequest httpServletRequest, BindingResult result
    ) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(service.register(request, getURL(httpServletRequest)));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@Param("code") String code) {
        return ResponseEntity.ok(service.verifyEmail(code));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("email") String email, HttpServletRequest httpServletRequest)
            throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(service.resetPassword(email, getURL(httpServletRequest)));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Param("token") String token, @RequestBody Map<String, String> newPassword) {
        return ResponseEntity.ok(service.changePassword(token, newPassword.get("newPassword"))); // @TODO make this a RequestBody properly
    }

    private String getURL(HttpServletRequest request) {
        return request.getRequestURL().toString().replace(request.getServletPath(), "");
    }

}
