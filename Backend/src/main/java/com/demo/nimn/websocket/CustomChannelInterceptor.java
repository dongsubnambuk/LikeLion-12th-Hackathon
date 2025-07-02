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
import java.util.Objects;
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
            String userEmail = accessor.getFirstNativeHeader("userEmail");
            if (userEmail == null) {
                // HandshakeInterceptor에서 설정한 속성에서 가져오기
                Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
                if (sessionAttributes != null) {
                    userEmail = (String) sessionAttributes.get("userEmail");
                }
            }

            String sessionId = accessor.getSessionId();

            if (userEmail != null && sessionId != null) {
                sessionIdToUserIdMap.put(sessionId, userEmail);

                log.info("[WebSocket CONNECT] sessionId={}, userEmail={}", sessionId, userEmail);

                Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
                if (sessionAttributes != null && sessionAttributes.containsKey("login")) {
                    String login = (String) sessionAttributes.get("login");
                    log.info("[WebSocket CONNECT] Using login from handshake: {}", login);

                    StompPrincipal principal = new StompPrincipal(login);
                    accessor.setUser(principal);
                } else {
                    StompPrincipal principal = new StompPrincipal(userEmail);
                    accessor.setUser(principal);
                }

                log.info("[WebSocket CONNECT] Principal set: {}", Objects.requireNonNull(accessor.getUser()).getName());
                log.info("[WebSocket CONNECT] Session attributes: {}", sessionAttributes);
            }
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            String sessionId = accessor.getSessionId();
            if (sessionId != null) {
                removeSession(sessionId);
            }
        }

        return message;
    }

    @Override
    public void postSend(@NotNull Message<?> message, @NotNull MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand()) && sent) {
            String sessionId = accessor.getSessionId();
            String userEmail = sessionIdToUserIdMap.get(sessionId);
            log.info("[WebSocket CONNECT SUCCESS] sessionId={}, userEmail={}", sessionId, userEmail);
        }
    }

    public void removeSession(String sessionId) {
        String userEmail = sessionIdToUserIdMap.remove(sessionId);
        log.info("[WebSocket DISCONNECT] sessionId={}, userEmail={}", sessionId, userEmail);
    }

    public boolean isConnected(String userEmail) {
        boolean connected = sessionIdToUserIdMap.containsValue(userEmail);
        log.debug("[Connection Check] userEmail={}, connected={}", userEmail, connected);
        return connected;
    }
}