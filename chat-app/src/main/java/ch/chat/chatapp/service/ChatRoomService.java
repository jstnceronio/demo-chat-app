package ch.chat.chatapp.service;

import ch.chat.chatapp.model.ChatRoom;
import ch.chat.chatapp.model.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    public String getOrCreateChatRoom(Long senderId, Long recipientId) {
        ChatRoom chatRoom = chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId);
        if (chatRoom != null) {
            return chatRoom.getChatId();
        } else {
            return createChat(senderId, recipientId);
        }
    }

    private String createChat(Long senderId, Long recipientId) {
        var chatId = String.format("%s_%s", senderId, recipientId); // for example lukas_aaron

        ChatRoom senderRecipient = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        ChatRoom recipientSender = ChatRoom.builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return chatId;
    }
}
