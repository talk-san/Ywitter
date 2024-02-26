package com.talxan.ywitter.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.talxan.ywitter.post.like.Like;
import com.talxan.ywitter.yuser.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue
    private Integer postId;
    private String text;
    private Date postedAt;

    @ManyToOne
    @JsonIgnore
    private User postYuser;

    @ManyToOne
    @JsonIgnore
    private Post parentPost;

    @Transient
    private List<Post> comments = new ArrayList<>(); // should I keep this?

    @OneToMany(mappedBy = "likePost", cascade = CascadeType.ALL)
    private List<Like> likes;

}
