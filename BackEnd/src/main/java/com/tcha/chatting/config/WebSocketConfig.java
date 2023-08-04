package com.tcha.chatting.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer { // (1)

//    private final StompHandler stompHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) { // (2)
        registry.enableSimpleBroker("/sub"); // (3)
        registry.setApplicationDestinationPrefixes("/pub"); // (4)
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { // (5)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
    }

    //웹 소켓 연결 및 끊길때 추가 기능을 위한 interceptor
//    @Override // (6)
//    public void configureClientInboundChannel (ChannelRegistration registration){
//        registration.interceptors(stompHandler);
//    }
}