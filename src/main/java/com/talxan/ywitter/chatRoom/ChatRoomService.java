package com.talxan.ywitter.chatRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(Integer senderId, Integer receiverId, boolean createNewRoomIfNotExist) {
        return chatRoomRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (createNewRoomIfNotExist) {
                        var chatId = createChatId(senderId, receiverId);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }

    private String createChatId(Integer senderId, Integer receiverId) {
        var chatId = String.format(("%s_%s"), senderId, receiverId);

        ChatRoom senderReceiver = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        ChatRoom receiverSender = ChatRoom.builder()
                .chatId(chatId)
                .senderId(receiverId)
                .receiverId(senderId)
                .build();

        chatRoomRepository.save(senderReceiver);
        chatRoomRepository.save(receiverSender);

        return chatId;
    }

}
