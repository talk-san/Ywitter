package com.talxan.ywitter.yuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM yuser u WHERE u.yuser_id " +
            "IN (SELECT f.yuser_id FROM yuser u INNER join following f ON u.yuser_id = f.following_id WHERE f.following_id = :yuserId)", nativeQuery = true)
    List<User> findFollowersByYuserId(Integer yuserId);

    Optional<User> findByVerificationToken(String verification_token);

    @Query("SELECT u FROM User u JOIN u.passwordResetTokens prt WHERE prt.resetToken = :token")
    Optional<User> findByResetToken(String token);
}
