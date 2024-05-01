package com.talxan.ywitter.like;

import com.talxan.ywitter.post.Post;
import com.talxan.ywitter.yuser.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByLikedPost(Post post);

    @Transactional
    @Modifying
    @Query("DELETE FROM Like l WHERE l.likedPost = :post AND l.yuser = :user")
    void deleteByPostIdAndUserId(Post post, User user);
}

