package com.exercise.oauth2jwt.config;

import com.exercise.oauth2jwt.filter.JwtAuthenticationFilter;
import com.exercise.oauth2jwt.filter.JwtAuthorizationFilter;
import com.exercise.oauth2jwt.repository.UserRepository;
import com.exercise.oauth2jwt.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private CorsConfig corsConfig;
    private CustomOAuth2UserService customOAuth2UserService;
    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .apply(new MyCustomDsl()) // 커스텀 필터 등록
            .and()
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/api/user")
                .access(null/*"hasAnyRole('SCOPE_profile','SCOPE_profile_image','SCOPE_email')"*/)
                .requestMatchers("/")
                .permitAll()
                .anyRequest().authenticated())
            // 기본 로그인 페이지 넣어야
            .oauth2Login(oauth2 -> oauth2.userInfoEndpoint(
                userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService)))
            .exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            .and()
            .build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception{
            // 달아주면 반드시 전달해 줘야 하는 파라미터가 있다 : AuthenticationManager(이 녀석을 통해서 로그인을 진행하기 때문에)
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                // 모든 요청이 이 필터를 타게 됨, 서버가 cors정책에서 벗어날 수 있게 된다 (크로스 오리진 요청이 와도 다 허용)
                .addFilter(corsConfig.corsFilter())
                .addFilter(new JwtAuthenticationFilter(authenticationManager))
                .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }
}
