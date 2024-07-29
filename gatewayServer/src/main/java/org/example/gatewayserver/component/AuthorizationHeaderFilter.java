package org.example.gatewayserver.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
//import org.example.gatewayserver.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextImpl;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpHeaders;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private Key secretKey;

    public AuthorizationHeaderFilter(@Value("${spring.jwt.secret}") String secret){
        super(Config.class);

        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        secretKey = Keys.hmacShaKeyFor(byteSecretKey);
    }


    @Override
    public GatewayFilter apply(Config config) {
        System.out.println("필터시작");
        return (exchange, chain) -> {
            String requiredRole = config.getRequiredRole(); // 필터 설정에 필요한 역할
            ServerHttpRequest request = exchange.getRequest(); // 현재 HTTP 요청
            String token = "";
            String authorizationHeader = "";
            String jwt = "";


            // 필터를 적용하지 않을 경로
            List<String> excludedPaths = List.of("/users/login", "/users/signup");

            // 현재 요청 경로가 제외된 경로에 해당하는지 확인
            String path = request.getURI().getPath();
            if (excludedPaths.contains(path)) {
                return chain.filter(exchange);
            }


            try {
                token = String.valueOf(request.getQueryParams().get("token"));
            } catch (Exception e){
            }

            // Authorization 헤더 없다면 에러
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION) && token == "null")
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
                //헤더값
            else if(token != "null"){
                jwt = token.replace("Bearer ", "").replace("[", "").replace("]", "");
            }
            else {
                authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                jwt = authorizationHeader.replace("Bearer ", "");
            }

            // jwt
            if (!isJwtValid(jwt)) return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);

            String userRole = resolveTokenRole(jwt).replace("[", "").replace("]", "");

            // 인가처리
            if(requiredRole.equalsIgnoreCase("role_admin")){
                if(!userRole.equalsIgnoreCase("role_admin")) return onError(exchange, "do not have permission", HttpStatus.FORBIDDEN);
            }else if(requiredRole.equalsIgnoreCase("role_user")){
                if(!userRole.equalsIgnoreCase("role_user") && !userRole.equalsIgnoreCase("role_admin"))
                    return onError(exchange, "do not have permission", HttpStatus.FORBIDDEN);
            }
            return chain.filter(exchange);


        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    //JWT에서 Role 정보 추출
    private String resolveTokenRole(String token){
        try {
            String subject = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody().get("role").toString();
            return subject;
        }catch (Exception e){
            return "e";
        }
    }

    //JWT에서 User 정보 추출
    private String resolveTokenUser(String token){
        try {
            String subject = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("sub").toString();
            return subject;
        }catch (Exception e){
            return "e";
        }
    }

    //JWT가 유효한지 확인
    private boolean isJwtValid(String token) {
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static class Config {
        private String requiredRole;

        public String getRequiredRole() {
            return requiredRole;
        }

        public void setRequiredRole(String requiredRole) {
            this.requiredRole = requiredRole;
        }
    }
}
