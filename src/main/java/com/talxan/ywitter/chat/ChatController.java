package com.talxan.ywitter.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessages chatMessages) {

        ChatMessages savedMessage = chatMessageService.save(chatMessages);

        simpMessagingTemplate.convertAndSendToUser(
                savedMessage.getReceiverId().toString(),
                "queue/messages",
                ChatNotification.builder()
                        .id(savedMessage.getId())
                        .senderId(savedMessage.getSenderId())
                        .receiverId(savedMessage.getReceiverId())
                        .message(savedMessage.getMessage())
                        .build()
        );
    }

    @GetMapping("/messages/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessages>> findChatMessage(
            @PathVariable("senderId") Integer senderId,
            @PathVariable("receiverId") Integer receiverId) {

        return ResponseEntity.ok(chatMessageService.findMessages(senderId,receiverId));
    }
}
