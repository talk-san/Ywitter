package com.talxan.securitylearn.auth;

import com.talxan.securitylearn.config.JwtService;
import com.talxan.securitylearn.exceptions.UserNotFoundException;
import com.talxan.securitylearn.user.Role;
import com.talxan.securitylearn.user.User;
import com.talxan.securitylearn.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User with email " + email + " is not found!"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password)
        );
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
