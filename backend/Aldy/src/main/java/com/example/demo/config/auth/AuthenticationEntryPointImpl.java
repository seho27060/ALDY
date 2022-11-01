package com.example.demo.config.auth;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        final Map<String, Object> body = new HashMap<>();
/*
{
  "timestamp": "2022-10-31T11:09:58.066444",
  "status": 404,
  "error": "NOT_FOUND",
  "code": "MEMBER_NOT_FOUND",
  "message": "해당 유저 정보를 찾을 수 없습니다"
}
 */
        LocalDateTime timestamp = LocalDateTime.now();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("timestamp", timestamp.toString());
        body.put("code", "UNAUTHORIZED");
        body.put("error", "NOT_FOUND");
        body.put("message", "권한이 없는 요청입니다.");

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);

    }

}

