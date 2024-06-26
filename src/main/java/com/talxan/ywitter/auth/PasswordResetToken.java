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
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resetId;

    @Column(unique = true)
    private String resetToken;
    private Date tokenExpirationDate;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "yuser_id")
    private User yuser;

}
