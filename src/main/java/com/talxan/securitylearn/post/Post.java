package com.talxan.securitylearn.post;


import com.talxan.securitylearn.user.User;
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
    private User postUser;

}
