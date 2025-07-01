package com.demo.nimn.websocket;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(
            @NotNull ServerHttpRequest request,
            @NotNull WebSocketHandler wsHandler,
            @NotNull Map<String,Object> attributes
    ) {
        String email = ((ServletServerHttpRequest)request)
                .getServletRequest()
                .getParameter("userEmail");
        return new StompPrincipal(email);
    }
}