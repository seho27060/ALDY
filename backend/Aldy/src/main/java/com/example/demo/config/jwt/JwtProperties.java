package com.example.demo.config.jwt;

public interface JwtProperties {
    String SECRET = "secretkey";
    int EXPIRATION_TIME = 864000000;
    String TOKEN_PREFIX = "Bearer";
    String HEADER_STRING = "Authorization";
}
