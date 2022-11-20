package com.example.demo.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        final Map<String, Object> body = new HashMap<>();
        AtomicReference<String> code = new AtomicReference<>("ACCESSTOKEN_EXPIRED");
        AtomicReference<String> message = new AtomicReference<>("만료된 액세스 토큰입니다.");
        LocalDateTime timestamp = LocalDateTime.now();

        String token = Optional.ofNullable(request.getHeader("Authorization")).orElseGet(() -> {
            code.set("NOT_FOUND");
            message.set("인증되지 않은 요청입니다.");
            return "";
        });

        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("timestamp", timestamp.toString());
        body.put("code",code);
        body.put("error", "UNAUTHORIZED");
        body.put("message", message);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}

