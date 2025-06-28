package com.demo.nimn.config;

import com.demo.nimn.websocket.CustomChannelInterceptor;
import com.demo.nimn.websocket.CustomHandshakeHandler;
import com.demo.nimn.websocket.CustomHandshakeInterceptor;
import com.demo.nimn.websocket.CustomWebSocketHandlerDecoratorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@EnableScheduling
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final CustomChannelInterceptor customChannelInterceptor;
    private final CustomWebSocketHandlerDecoratorFactory customWebSocketHandlerDecoratorFactory;
    private final CustomHandshakeInterceptor customHandshakeInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue", "/topic");  // queue
        config.setApplicationDestinationPrefixes("/app");  // 클라이언트 전송
        config.setUserDestinationPrefix("/user");  // user prefix 설정
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/notification")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .setAllowedOrigins("http://127.0.0.1:3000", "http://nimn.store")
                .withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setDecoratorFactories(customWebSocketHandlerDecoratorFactory);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(customChannelInterceptor);
    }
}
