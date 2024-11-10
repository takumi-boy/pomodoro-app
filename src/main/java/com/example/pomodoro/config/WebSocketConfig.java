package com.example.pomodoro.config;

import com.example.pomodoro.websocket.TimerWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(timerWebSocketHandler(), "/ws/timer").setAllowedOrigins("*");
    }

    @Bean
    public TimerWebSocketHandler timerWebSocketHandler() {
        return new TimerWebSocketHandler();
    }
}
