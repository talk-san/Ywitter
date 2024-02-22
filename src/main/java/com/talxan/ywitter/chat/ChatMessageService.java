package com.talxan.ywitter.chat;

import com.talxan.ywitter.chatRoom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessages save(ChatMessages chatMessages) {
        var chatId = chatRoomService.getChatRoomId(
                chatMessages.getSenderId(),
                chatMessages.getReceiverId(),
                true)
                .orElseThrow();

        chatMessages.setChatId(chatId);

        return chatMessageRepository.save(chatMessages);
    }

    public List<ChatMessages> findMessages(Integer senderId, Integer receiverId) {
        var chatId = chatRoomService.getChatRoomId(senderId,receiverId,false);
        return chatId.map(chatMessageRepository::findByChatId).orElse(new ArrayList<>());
    }

}
