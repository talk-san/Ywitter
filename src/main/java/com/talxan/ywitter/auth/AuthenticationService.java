package com.talxan.ywitter.auth;

import com.talxan.ywitter.config.JwtService;
import com.talxan.ywitter.exceptions.EmailAlreadyTakenException;
import com.talxan.ywitter.exceptions.TokenInvalidException;
import com.talxan.ywitter.exceptions.UserNotFoundException;
import com.talxan.ywitter.yuser.User;
import com.talxan.ywitter.yuser.UserRepository;
import com.talxan.ywitter.yuser.UserService;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
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
    private final PasswordResetTokenRepository passwordResetTokenRepositoryRepository;

    public AuthenticationResponse register(RegisterRequest request, String url) throws MessagingException, UnsupportedEncodingException {
        String email = request.getEmail();
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new EmailAlreadyTakenException(email);
        });

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
        //String verifyUrl = url + "/api/v1/auth/verify?code=" + user.getVerificationToken();
        String verifyUrl = "http://localhost:3000/verify?code=" + user.getVerificationToken(); // this should be with url but with localhost:3030, current is 8080

        mailSender(user, toAddress, fromAddress, senderName, subject, content, verifyUrl);
    }

    public String verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token).orElseThrow(() -> new UserNotFoundException("User with verification token does not exist or token is null"));
        if (user.isEnabled()) {
            return "Account is already verified!";
        } else {
            user.setVerificationToken(null);
            user.setEnabled(true);
            userRepository.save(user);
            return "Account successfully verified!";
        }
    }

    private void sendPasswordResetMail(User user, String url, String resetToken) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "csywitter@gmail.com";
        String senderName = "Ywitter";
        String subject = "Click here to reset your Ywitter password";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to reset your password: [[verifyUrl]]";
        //String verifyUrl = url + "/api/v1/auth/reset?token=" + user.getVerificationToken();
        String verifyUrl = "http://localhost:3000/reset?token=" + resetToken; // this should be with url but with localhost:3030, current is 8080

        mailSender(user, toAddress, fromAddress, senderName, subject, content, verifyUrl);
    }

    private void createPasswordResetToken(User yuser, String token) {
        passwordResetTokenRepositoryRepository.save(PasswordResetToken.builder()
                .resetToken(token)
                .yuser(yuser)
                .tokenExpirationDate(Date.from((LocalDateTime.now()).plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant()))
                .build());
    }

    public String resetPassword(String email, String url) throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        String token = generateToken();
        createPasswordResetToken(user, token);
        sendPasswordResetMail(user, url, token);
        return "Token generated, saved, email sent";
    }

    public String changePassword(String token, String newPassword) {
        System.out.println("Do we get here?");
        PasswordResetToken actualToken = passwordResetTokenRepositoryRepository.findByResetToken(token);
        System.out.println("Actual Token:" + actualToken);
        if (actualToken == null || actualToken.getTokenExpirationDate().before(Calendar.getInstance().getTime())
                || !actualToken.getResetToken().equals(token)) {
            throw new TokenInvalidException("Token not found or expired!");
        }
        User user = userRepository.findByResetToken(token).orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        System.out.println("Password got changed");
        return "Password changed successfully";
    }

    private void mailSender(User user, String toAddress, String fromAddress, String senderName, String subject, String content, String verifyUrl) throws MessagingException, UnsupportedEncodingException {
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


}
