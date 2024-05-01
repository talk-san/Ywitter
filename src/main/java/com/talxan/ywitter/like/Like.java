package com.talxan.ywitter.like;

import com.talxan.ywitter.post.Post;
import com.talxan.ywitter.yuser.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post_likes")
public class Like {

    @Id
    @GeneratedValue
    private Integer likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yuser_id")
    private User yuser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post likedPost;
}
