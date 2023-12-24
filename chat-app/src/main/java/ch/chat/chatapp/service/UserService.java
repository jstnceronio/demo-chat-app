package ch.chat.chatapp.service;

import ch.chat.chatapp.model.Status;
import ch.chat.chatapp.model.User;
import ch.chat.chatapp.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void connect(User user) {
        user.setStatus(Status.ONLINE);
        repository.save(user);
    }

    public void disconnect(User user) {
        User connectedUser = repository.findOneByName(user.getName());
        if (connectedUser != null) {
            connectedUser.setStatus(Status.OFFLINE);
            repository.save(connectedUser);
        }
    }

    public List<User> getConnectedUsers() {
        return repository.findAllByStatus(Status.ONLINE);
    }
}
