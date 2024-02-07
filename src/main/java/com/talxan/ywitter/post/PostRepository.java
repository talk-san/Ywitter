package com.talxan.ywitter.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findFirst10ByPostYuser_YuserIdInOrderByPostedAtDesc(List<Integer> userIds);
}
