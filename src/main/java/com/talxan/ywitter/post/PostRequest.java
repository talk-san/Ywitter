package com.talxan.ywitter.post;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @NotBlank
    private String text;
}
