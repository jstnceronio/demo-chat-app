package ch.chat.chatapp.service;

import ch.chat.chatapp.model.Message;
import ch.chat.chatapp.model.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Message message) {
        // Here, you can add any business logic before saving the message
        return messageRepository.save(message);
    }

    public List<Message> findAll(){
        return messageRepository.findAll();
    }


    // Additional methods for other business logic (e.g., retrieving messages)
}

