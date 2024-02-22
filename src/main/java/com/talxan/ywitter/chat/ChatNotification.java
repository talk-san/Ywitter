package com.talxan.ywitter.chat;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ChatNotification {

    private String id;
    private Integer senderId;
    private Integer receiverId;
    private String message;

}
