package com.exercise.oauth2jwt.service;

import com.exercise.oauth2jwt.model.ProviderUser;
import com.exercise.oauth2jwt.model.Users;
import com.exercise.oauth2jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public void register(String registrationId, ProviderUser providerUser) {

        Users user = Users.builder().registrationId(registrationId)
            .oauthId(providerUser.getId())
            .username(providerUser.getUsername())
            .password(providerUser.getPassword())
//            .authorities(providerUser.getAuthorities())
            .provider(providerUser.getProvider())
            .email(providerUser.getEmail())
            .build();

        userRepository.register(user);
    }
}
