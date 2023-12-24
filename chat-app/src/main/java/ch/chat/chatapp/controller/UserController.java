package ch.chat.chatapp.controller;

import ch.chat.chatapp.model.User;
import ch.chat.chatapp.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService service;


    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public User connect(@Payload User user) {
        service.connect(user);
        return user;
    }

    public User disconnect(@Payload User user) {
        service.disconnect(user);
        return user;
    }

    public UserController(UserService service) {
        this.service = service;
    }
}
