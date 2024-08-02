package org.example.gatewayserver.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*"); // 필요시 특정 도메인으로 변경
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        }));
    }

    @Bean
    public Ordered customGlobalFilterOrder() {
        return () -> Ordered.LOWEST_PRECEDENCE;
    }
}