package com.talxan.securitylearn.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM user u WHERE u.user_id " +
            "IN (SELECT f.user_id FROM user u INNER join following f ON u.user_id = f.following_id WHERE f.following_id = :userId)", nativeQuery = true)
    List<User> findFollowersByUserId(Integer userId);

}
