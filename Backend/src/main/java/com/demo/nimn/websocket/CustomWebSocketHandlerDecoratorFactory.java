package com.demo.nimn.websocket;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

@Component
@RequiredArgsConstructor
public class CustomWebSocketHandlerDecoratorFactory implements WebSocketHandlerDecoratorFactory {

    private final ChannelInterceptor channelInterceptor;

    @NotNull
    @Override
    public WebSocketHandler decorate(@NotNull WebSocketHandler handler) {
        return new WebSocketHandlerDecorator(handler) {

            @Override
            public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
                super.afterConnectionEstablished(session);
                String sessionId = session.getId();
                System.out.println("[WebSocket OPEN] sessionId=" + sessionId);
            }

            @Override
            public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus closeStatus) throws Exception {
                super.afterConnectionClosed(session, closeStatus);
                String sessionId = session.getId();

                // 세션 정보 제거
                channelInterceptor.removeSession(sessionId);
            }
        };
    }
}