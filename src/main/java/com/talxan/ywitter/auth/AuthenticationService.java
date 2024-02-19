package com.talxan.ywitter.auth;

import com.talxan.ywitter.config.JwtService;
import com.talxan.ywitter.exceptions.EmailAlreadyTakenException;
import com.talxan.ywitter.exceptions.UserNotFoundException;
import com.talxan.ywitter.yuser.User;
import com.talxan.ywitter.yuser.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final int TOKEN_LENGTH = 64;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final JavaMailSender mailSender;

    public AuthenticationResponse register(RegisterRequest request, String url) throws MessagingException, UnsupportedEncodingException {
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
                .enabled(false)
                .verificationToken(generateToken())
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        sendVerificationMail(user, url);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User with email " + email + " is not found!"));
        if (!user.isEnabled()) throw new UserNotFoundException("Account not yet verified.");

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

    private void sendVerificationMail(User user, String url) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "csywitter@gmail.com";
        String senderName = "Ywitter";
        String subject = "Click here to verify your Ywitter account";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration: [[verifyUrl]]";
        String verifyUrl = url + "/verify?code=" + user.getVerificationToken();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName());
        content = content.replace("[[verifyUrl]]", verifyUrl);

        helper.setText(content, true);

        mailSender.send(message);
    }

    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes).substring(0, TOKEN_LENGTH);
    }

    public boolean verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token).orElseThrow(UserNotFoundException::new);
        if (user.isEnabled()) {
            return false;
        } else {
            user.setVerificationToken(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
    }
}
