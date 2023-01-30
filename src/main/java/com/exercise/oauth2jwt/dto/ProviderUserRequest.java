package com.exercise.oauth2jwt.dto;

import com.exercise.oauth2jwt.model.Users;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public record ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User, Users user) {
    public ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        this(clientRegistration, oAuth2User, null);
    }

    // form 인증 할 때 user 객체만 받을 수 있도록
    public ProviderUserRequest(Users user) {
        this(null, null, user);
    }
}
