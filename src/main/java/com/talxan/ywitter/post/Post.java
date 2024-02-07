package com.talxan.ywitter.post;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.talxan.ywitter.yuser.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    private String content;
    private Date postedAt;

    @ManyToOne
    @JsonIgnore
    private User postYuser;

}
