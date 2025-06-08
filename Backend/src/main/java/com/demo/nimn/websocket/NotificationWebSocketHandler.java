package com.demo.nimn.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationWebSocketHandler extends TextWebSocketHandler {

    //private static final ConcurrentHashMap<Long, List<WebSocketSession>> userSessions = new ConcurrentHashMap<>();
    private static final Map<String, List<WebSocketSession>> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        URI uri = session.getUri();
        String userId = UriComponentsBuilder.fromUri(uri)
                .build()
                .getQueryParams()
                .getFirst("userId");
        if (userId != null) {
            // 기존에 연결된 동일한 userId의 세션을 제거
            if (userSessions.containsKey(userId)) {
                List<WebSocketSession> existingSessions = userSessions.get(userId);
                existingSessions.forEach((sessions) -> {
                    existingSessions.remove(CloseStatus.NORMAL);
                });
            }
            userSessions.remove(userId);
            System.out.println("기존 세션 제거됨: User ID: " + userId);
            // 새로운 세션을 추가
            userSessions
                    .computeIfAbsent(userId, k -> (List<WebSocketSession>) new ConcurrentHashMap<>())
                    .add(session);
            session.getAttributes().put("userId", userId);
            System.out.println("새로운 WebSocket 연결: " + session.getId() + " (User ID: " + userId + ")");
        } else {
            System.out.println("WebSocket 연결 시 userId가 없음: " + session.getId());
            session.close(CloseStatus.BAD_DATA);
        }
        printAllSessions();

    }
    public void printAllSessions() {
        System.out.println("현재 연결된 모든 세션:");
        userSessions.forEach((userId, sessions) -> {
            System.out.println("User ID: " + userId);
            sessions.forEach(session ->
                    System.out.println("    Session ID: " + session.getId())
            );
        });
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        System.out.println("Received: " + payload);

        // Echo the received message back to the client
        try {
            session.sendMessage(new TextMessage("Echo: " + payload));
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }

//    public static List<WebSocketSession> getSessionsByUserId(Long userId) {
//        return userSessions.getOrDefault(userId, new ArrayList<>());
//    }
//
//    public static List<WebSocketSession> getSessionsByEmail(String email) {
//        return userSessions.getOrDefault(email, new ArrayList<>());
//    }
//
//    public static void addSession(Long userId, WebSocketSession session) {
//        userSessions.computeIfAbsent(userId, k -> new ArrayList<>()).add(session);
//    }
//
//    public static void removeSession(Long userId, WebSocketSession session) {
//        List<WebSocketSession> sessions = userSessions.get(userId);
//        if (sessions != null) {
//            sessions.remove(session);
//            if (sessions.isEmpty()) {
//                userSessions.remove(userId);
//            }
//        }
//    }
}
