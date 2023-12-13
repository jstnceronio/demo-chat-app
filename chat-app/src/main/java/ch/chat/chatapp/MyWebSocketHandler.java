package ch.chat.chatapp;

import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;

public class MyWebSocketHandler extends TextWebSocketHandler {

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        System.out.println(message + " " + TimeStampHandler.getCurrentTimestamp());

        // Durchlaufen aller Sessions und Senden der Nachricht an jede Session
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen() & (webSocketSession != session)) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws InterruptedException, IOException {
        // username rauslesen
        String username = session.getHandshakeHeaders().getFirst("Username");
        System.out.println(username + " ist jetzt im Chat. " + TimeStampHandler.getCurrentTimestamp());

        // broadcast
        sessions.add(session);

        // Erstellen der Willkommensnachricht
        TextMessage welcomeMessage = new TextMessage(username + " ist jetzt im Chat.");

        // Senden der Willkommensnachricht an alle verbundenen Sessions
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(welcomeMessage);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws InterruptedException, IOException {

        // username rauslesen
        String username = session.getHandshakeHeaders().getFirst("Username");
        System.out.println(username + " hat den Chat verlassen. " + TimeStampHandler.getCurrentTimestamp());

        // Erstellen der Quit Nachricht
        TextMessage quitMessage = new TextMessage(username + " hat den Chat verlassen.");

        // Senden der Quit Nachricht an alle verbundenen Sessions
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen() & (webSocketSession != session)) {
                webSocketSession.sendMessage(quitMessage);
            }
        }

        // Entfernen der Session aus der Liste
        sessions.remove(session);

    }
}
