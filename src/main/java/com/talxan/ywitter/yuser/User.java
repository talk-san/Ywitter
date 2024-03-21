package com.talxan.ywitter.yuser;

import com.talxan.ywitter.auth.PasswordResetToken;
import com.talxan.ywitter.post.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yuser")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer yuserId;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photoUrl;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "verification_token", length = 64)
    private String verificationToken;

    @OneToOne(mappedBy = "yuser", cascade = CascadeType.ALL)
    private PasswordResetToken passwordResetToken;

    @OneToMany(mappedBy = "postYuser", fetch = FetchType.EAGER)
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "following",
            joinColumns = @JoinColumn(name = "yuser_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    @Builder.Default
    private List<User> following = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_likes",
            joinColumns = @JoinColumn(name = "yuser_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    @Builder.Default
    private List<Post> likedPosts = new ArrayList<>();

    @Enumerated
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getEnabled();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        return Objects.equals(this.yuserId, other.yuserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(yuserId);
    }

    @Override
    public String toString() {
        return "YUser{" +
                "yuserId=" + yuserId +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
