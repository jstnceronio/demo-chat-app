package ch.chat.chatapp.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // Custom database operations if needed
}
