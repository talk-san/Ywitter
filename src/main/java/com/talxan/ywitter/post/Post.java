package com.talxan.ywitter.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.talxan.ywitter.yuser.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @ManyToMany(mappedBy = "likedPosts", fetch = FetchType.EAGER)
    private List<User> likes = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Post other = (Post) obj;
        return Objects.equals(this.postId, other.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId);
    }


}
