package ch.chat.chatapp.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByName(String name);
    List<User> findAllByStatus(Status status);
}
