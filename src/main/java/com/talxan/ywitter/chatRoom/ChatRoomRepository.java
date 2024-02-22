package com.talxan.ywitter.chatRoom;

import com.talxan.ywitter.yuser.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findByIdOfSenderAndReceiver(Integer senderId, Integer receiverId);
}
