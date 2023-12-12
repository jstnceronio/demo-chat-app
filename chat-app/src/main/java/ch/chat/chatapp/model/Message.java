package ch.chat.chatapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Message {
    @Id
    private Long id;
    private String content;

}
