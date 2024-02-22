package com.talxan.ywitter.chat;

import com.talxan.ywitter.chatRoom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessages, String> {
    List<ChatMessages> findByChatId(String s);

}
