package me.coldrain.learn.config;

import lombok.RequiredArgsConstructor;
import me.coldrain.learn.handler.ChatHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "ws/chat")
                .setAllowedOrigins("*");
    }
}