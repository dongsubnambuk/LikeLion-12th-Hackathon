package com.demo.nimn.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
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

                // 🔥 HandshakeInterceptor에서 설정한 login 속성 사용
                Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
                if (sessionAttributes != null && sessionAttributes.containsKey("login")) {
                    String login = (String) sessionAttributes.get("login");
                    log.info("[WebSocket CONNECT] Using login from handshake: {}", login);

                    // Principal을 login으로 설정 (HandshakeInterceptor와 일치)
                    StompPrincipal principal = new StompPrincipal(login);
                    accessor.setUser(principal);
                } else {
                    // fallback: userEmail로 Principal 설정
                    StompPrincipal principal = new StompPrincipal(userEmail);
                    accessor.setUser(principal);
                }

                log.info("[WebSocket CONNECT] Principal set: {}", accessor.getUser().getName());
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

        // CONNECT 후 추가 로깅
        if (StompCommand.CONNECT.equals(accessor.getCommand()) && sent) {
            String sessionId = accessor.getSessionId();
            String userEmail = sessionIdToUserIdMap.get(sessionId);
            log.info("[WebSocket CONNECT SUCCESS] sessionId={}, userEmail={}", sessionId, userEmail);
        }
    }

    // 세션 종료 시
    public void removeSession(String sessionId) {
        String userEmail = sessionIdToUserIdMap.remove(sessionId);
        log.info("[WebSocket DISCONNECT] sessionId={}, userEmail={}", sessionId, userEmail);
    }

    // 해당 유저가 연결되어 있는지 확인
    public boolean isConnected(String userEmail) {
        boolean connected = sessionIdToUserIdMap.containsValue(userEmail);
        log.debug("[Connection Check] userEmail={}, connected={}", userEmail, connected);
        return connected;
    }

    // 디버깅을 위한 메서드 추가
    public void printAllSessions() {
        log.info("=== Current Sessions ===");
        sessionIdToUserIdMap.forEach((sessionId, userEmail) ->
                log.info("SessionId: {}, UserEmail: {}", sessionId, userEmail));
        log.info("========================");
    }

    // 헬퍼 메서드: userEmail로 sessionId 찾기
    public String getSessionIdByUserEmail(String userEmail) {
        return sessionIdToUserIdMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(userEmail))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}