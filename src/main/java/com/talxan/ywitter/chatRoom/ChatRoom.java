package com.talxan.ywitter.chatRoom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chatRoom")
public class ChatRoom {

    @Id
    private String id;
    private String chatId;
    private Integer senderId;
    private Integer receiverId;


}
