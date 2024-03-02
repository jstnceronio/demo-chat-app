package ch.chat.chatapp.controller;

import ch.chat.chatapp.model.Message;
import ch.chat.chatapp.service.MessageService;
import ch.chat.chatapp.utils.TimeStampHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;

public class WebSocketHandler extends TextWebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public WebSocketHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {

        Message newMessage = new Message();
        String username = session.getHandshakeHeaders().getFirst("username");
        if (username == null) {
             username = Objects.requireNonNull(
                     session.getUri()
             ).getQuery().split("username=")[1];
        }
        newMessage.setContent(message
                .getPayload()
                .replace("\"", "")
        );
        newMessage.setSender(username);

        logger.info("Received: " + newMessage.getContent());

        messageService.saveMessage(newMessage);

        String returnMessage = String.format("{\"username\":\"%s\", \"message\":\"%s\"}",
                newMessage.getSender(),
                newMessage.getContent());

        session.sendMessage(new TextMessage(returnMessage));

        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen() && webSocketSession != session) {
                webSocketSession.sendMessage(new TextMessage(returnMessage));
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws InterruptedException, IOException {
        // read the username
        String username = session.getHandshakeHeaders().getFirst("username");
        if (username == null) {
            username = Objects.requireNonNull(
                    session.getUri()
            ).getQuery().split("username=")[1];
        }

        logger.info(username + " entered the chat @ " + TimeStampHandler.getCurrentTimestamp());

        // broadcast
        sessions.add(session);

        String welcomeMessage = username + " ist jetzt im Chat.";
        String returnMessage = String.format("{\"username\":\"System\", \"message\":\"%s\"}", welcomeMessage);

        // Send welcome message to all connected sessions
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(returnMessage));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws IOException {

        // Get username
        String username = session.getHandshakeHeaders().getFirst("username");
        if (username == null) {
            username = Objects.requireNonNull(
                    session.getUri()
            ).getQuery().split("username=")[1];
        }

        logger.info(username + " hast left the chat @ " + TimeStampHandler.getCurrentTimestamp());

        String quitMessage = username + " hat den Chat verlassen.";
        String returnMessage = String.format("{\"username\":\"System\", \"message\":\"%s\"}", quitMessage);

        // Send quit message to all connected sessions
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen() & (webSocketSession != session)) {
                webSocketSession.sendMessage(new TextMessage(returnMessage));
            }
        }

        // Remove session from session list
        sessions.remove(session);
    }
}
