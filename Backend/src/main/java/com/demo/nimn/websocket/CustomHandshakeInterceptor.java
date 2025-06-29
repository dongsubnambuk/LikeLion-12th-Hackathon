package com.demo.nimn.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
@Component
public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) throws Exception {

        String userEmail = null;

        if (request instanceof ServletServerHttpRequest servletRequest) {
            userEmail = servletRequest.getServletRequest().getParameter("userEmail");

            if (userEmail != null) {
                log.info("[Handshake] userEmail found in query parameter: {}", userEmail);
                attributes.put("userEmail", userEmail);

                attributes.put("login", userEmail);
                attributes.put("user", new StompPrincipal(userEmail));
            } else {
                log.warn("[Handshake] userEmail not found in query parameters");
            }
        }

        // Header에서도 확인
        if (userEmail == null) {
            userEmail = request.getHeaders().getFirst("userEmail");
            if (userEmail != null) {
                log.info("[Handshake] userEmail found in header: {}", userEmail);
                attributes.put("userEmail", userEmail);
                // 🔥 중요: login 속성 추가
                attributes.put("login", userEmail);
                attributes.put("user", new StompPrincipal(userEmail));
            }
        }

        if (userEmail == null) {
            log.error("[Handshake] userEmail not found in request");
            return false; // handshake 거부
        }

        return true;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception
    ) {
        if (exception != null) {
            log.error("[Handshake] Exception occurred: ", exception);
        } else {
            log.info("[Handshake] Completed successfully");
        }
    }
}