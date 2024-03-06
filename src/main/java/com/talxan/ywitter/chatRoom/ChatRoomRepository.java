package com.talxan.ywitter.chatRoom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderIdAndReceiverId(Integer senderId, Integer receiverId);
}
