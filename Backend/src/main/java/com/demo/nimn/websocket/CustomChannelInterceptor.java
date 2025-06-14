package com.demo.nimn.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
@RequiredArgsConstructor
public class CustomChannelInterceptor implements ChannelInterceptor {

    private final Map<String, String> sessionIdToUserIdMap = new ConcurrentHashMap<>();

    @Override
    public Message<?> preSend(@NotNull Message<?> message, @NotNull MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String userEmail = accessor.getFirstNativeHeader("userEmail");  // 클라이언트가 header에 넣어서 보내야 함
            String sessionId = accessor.getSessionId();

            if (userEmail != null && sessionId != null) {
                sessionIdToUserIdMap.put(sessionId, userEmail);

                // 연결 확인 로그
                log.info("[WebSocket CONNECT] sessionId={}, userEmail={}",  sessionId, userEmail);

                accessor.setUser(new StompPrincipal(userEmail));
            }
        }

        return message;
    }

    // 세션 종료 시
    public void removeSession(String sessionId) {
        String userEmail = sessionIdToUserIdMap.remove(sessionId);
        log.info("[WebSocket DISCONNECT] sessionId={}, userEmail={}",  sessionId, userEmail);
    }

    // 해당 유저가 연결되어 있는지 확인
    public boolean isConnected(String userEmail) {
        return sessionIdToUserIdMap.containsValue(userEmail);
    }
}

