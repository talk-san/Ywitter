package com.talxan.ywitter.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Integer postId;
    private String username;
    private String firstName;
    private String text;
    private Date postedAt;
    private String userPhoto;
    private Integer parentPostId;
    private Integer numOfLikes;
    private Integer numOfComments;
}
