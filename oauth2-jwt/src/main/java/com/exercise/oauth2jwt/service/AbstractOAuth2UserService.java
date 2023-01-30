package com.exercise.oauth2jwt.service;

import com.exercise.oauth2jwt.converters.ProviderUserConverter;
import com.exercise.oauth2jwt.dto.ProviderUserRequest;
import com.exercise.oauth2jwt.model.ProviderUser;
import com.exercise.oauth2jwt.model.Users;
import com.exercise.oauth2jwt.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public abstract class AbstractOAuth2UserService {
    private UserService userService;

    private UserRepository userRepository;

    private ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

    public void register(ProviderUser providerUser, OAuth2UserRequest userRequest){
        Users user = userRepository.findByUsername(providerUser.getUsername());

        if(user == null){
            ClientRegistration clientRegistration = userRequest.getClientRegistration();
            userService.register(clientRegistration.getRegistrationId(), providerUser);
        }else{
            System.out.println("userRequest = " + userRequest);
        }
    }

    public ProviderUser providerUser(ProviderUserRequest providerUserRequest){
        return providerUserConverter.convert(providerUserRequest);
    }
}
