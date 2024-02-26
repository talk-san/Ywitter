package com.talxan.ywitter.post.like;

import com.talxan.ywitter.post.Post;
import com.talxan.ywitter.yuser.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post_like")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likeId;

    @ManyToOne
    private User likeUser;

    @ManyToOne
    private Post likePost;

    @Override
    public String toString() {
        return "Like{" +
                "likeId=" + likeId +
                ", likeUserId=" + likeUser.getYuserId() +
                ", likePostId=" + likePost.getPostId() +
                '}';
    }
}
