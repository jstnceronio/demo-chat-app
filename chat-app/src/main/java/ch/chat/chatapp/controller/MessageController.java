package ch.chat.chatapp.controller;

import ch.chat.chatapp.model.Message;
import ch.chat.chatapp.model.Notification;
import ch.chat.chatapp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MessageController {
    private final SimpMessagingTemplate messagingTemplate; // Yeah, I'm a SIMP: Simple Messaging Protocol
    private final MessageService messageService;

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<Message>> getMessagesInChat(
            @PathVariable("senderId") Long senderId,
            @PathVariable("recipient") Long recipientId
    ) { return ResponseEntity.ok(messageService.getMessagesInChat(senderId, recipientId)); }


    @MessageMapping("/chat")
    public ResponseEntity<Message> receiveMessage(@Payload Message message) {
        Message savedMessage = messageService.saveMessage(message);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(message.getRecipient().getId()),
                "/queue/messages",
                Notification.builder()
                        .id(savedMessage.getId())
                        .senderId(savedMessage.getSender().getId())
                        .recipientId(savedMessage.getRecipient().getId())
                        .content(savedMessage.getContent())
                        .build()
        );
        return ResponseEntity.ok(savedMessage);
    }
}

