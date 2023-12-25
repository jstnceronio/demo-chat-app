package ch.chat.chatapp.service;

import ch.chat.chatapp.model.Message;
import ch.chat.chatapp.model.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;

    public Message saveMessage(Message message) {
        String chatRoomId = chatRoomService.getOrCreateChatRoom(
                message.getSender().getId(),
                message.getRecipient().getId()
        );
        message.setChatId(chatRoomId);
        return messageRepository.save(message);
    }

    public List<Message> getMessagesInChat(Long senderId, Long recipientId) {
        String chatId = chatRoomService.getOrCreateChatRoom(senderId, recipientId);
        List<Message> messages = new ArrayList<>();
        messages.add(messageRepository.findByChatId(chatId));
        return messages;
    }
    public List<Message> findAll(){
        return messageRepository.findAll();
    }


    // Additional methods for other business logic (e.g., retrieving messages)
}

