package com.exercise.oauth2jwt.constant;

public interface JwtProperties {

    String SECRET = "justOnlyUsSecret";
    int EXPIRATION_TIME = 864000000; // 10일
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

}