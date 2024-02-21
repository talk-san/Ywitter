package com.talxan.ywitter.auth;


import com.talxan.ywitter.yuser.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (@Valid @RequestBody RegisterRequest request, HttpServletRequest httpServletRequest) throws MessagingException, UnsupportedEncodingException {
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
        if (service.verifyEmail(code)) {
            return ResponseEntity.ok("Email verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code");
        }
    }

    @GetMapping("/reset")
    public ResponseEntity<String> resetPassword(String token) {
        return ResponseEntity.ok(service.resetPassword(token));
    }


    private String getURL(HttpServletRequest request) {
        return request.getRequestURL().toString().replace(request.getServletPath(), "");
    }

}
