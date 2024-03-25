package com.talxan.ywitter.auth;

import com.talxan.ywitter.yuser.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class PasswordResetToken { // This for some reason doesn't throws an error when adding multiple reset tokens for the same user

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resetId;

    @Column(unique = true)
    private String resetToken;
    private Date tokenExpirationDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "yuser_id")
    private User yuser;

}
