package com.talxan.ywitter.post.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findLikeByLikePost_PostIdAndLikeUser_YuserId(Integer postId, Integer userId);
}