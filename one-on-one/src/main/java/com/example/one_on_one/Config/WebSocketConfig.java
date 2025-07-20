package com.example.one_on_one.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 👈 This must match your frontend
                .setAllowedOriginPatterns("*") // For development only; restrict in prod
                .withSockJS(); // Enable SockJS fallback

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); // For @MessageMapping
        registry.enableSimpleBroker("/topic"); // For @SendTo topics
    }
}
