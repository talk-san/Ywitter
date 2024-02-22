package com.talxan.ywitter.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chatMessages")
public class ChatMessages {

    @Id
    private String id;
    private String chatId;
    private Integer senderId;
    private Integer receiverId;
    private String message;
    private Date timestamp;

}
