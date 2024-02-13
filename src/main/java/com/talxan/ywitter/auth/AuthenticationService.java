package com.talxan.ywitter.auth;

import com.talxan.ywitter.config.JwtService;
import com.talxan.ywitter.exceptions.EmailAlreadyTakenException;
import com.talxan.ywitter.exceptions.UserNotFoundException;
import com.talxan.ywitter.yuser.User;
import com.talxan.ywitter.yuser.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        String email = request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            throw new EmailAlreadyTakenException(email);
        }

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
