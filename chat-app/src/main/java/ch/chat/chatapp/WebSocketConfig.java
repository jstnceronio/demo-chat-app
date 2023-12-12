package ch.chat.chatapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/websocket");
    }

    public MyWebSocketHandler myHandler() {
        return new MyWebSocketHandler();
    }
}

/*
 * package ch.chat.chatapp;
 * 
 * import org.springframework.context.annotation.Configuration;
 * import org.springframework.messaging.simp.config.MessageBrokerRegistry;
 * import org.springframework.web.socket.config.annotation.
 * EnableWebSocketMessageBroker;
 * import
 * org.springframework.web.socket.config.annotation.StompEndpointRegistry;
 * import org.springframework.web.socket.config.annotation.
 * WebSocketMessageBrokerConfigurer;
 * 
 */

/*
 * package ch.chat.chatapp;
 * 
 * import org.springframework.context.annotation.Configuration;
 * import org.springframework.messaging.simp.config.MessageBrokerRegistry;
 * import org.springframework.web.socket.config.annotation.
 * EnableWebSocketMessageBroker;
 * import
 * org.springframework.web.socket.config.annotation.StompEndpointRegistry;
 * import org.springframework.web.socket.config.annotation.
 * WebSocketMessageBrokerConfigurer;
 * 
 * @Configuration
 * 
 * @EnableWebSocketMessageBroker
 * public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
 * 
 * @Override
 * public void registerStompEndpoints(StompEndpointRegistry registry) {
 * registry.addEndpoint("/websocket").setAllowedOriginPatterns("*");
 * // registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
 * 
 * }
 * 
 * @Override
 * public void configureMessageBroker(MessageBrokerRegistry registry) {
 * registry.enableSimpleBroker("/topic");
 * registry.setApplicationDestinationPrefixes("/app");
 * }
 * }
 */