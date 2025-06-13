package com.demo.nimn.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionManageService {
    private final ChannelInterceptor channelInterceptor;

    // 중간 서비스로 분리
    public boolean isUserConnected(String userEmail) {
        return channelInterceptor.isConnected(userEmail);
    }
}
