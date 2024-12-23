package com.example.prography_quest.global.common.config;


import com.example.prography_quest.global.common.jwt.JwtAuthenticationFilter;
import com.example.prography_quest.global.common.jwt.JwtProvider;
import com.example.prography_quest.global.common.response.SecurityResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final JwtProvider jwtProvider;

    @RequiredArgsConstructor
    public static class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        private final JwtProvider jwtProvider;

        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtProvider);
            http.addFilter(jwtAuthenticationFilter);
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.apply(new CustomSecurityFilterManager(jwtProvider));
        http.exceptionHandling(configurer -> configurer.authenticationEntryPoint((request, response, accessDeniedException) -> SecurityResponse.unAuthentication(response)));
        http.exceptionHandling(configurer -> configurer.accessDeniedHandler((request, response, accessDeniedException) -> SecurityResponse.forbidden(response)));
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/users/profile", "POST")).authenticated());
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/users/profile", "PUT")).authenticated());
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/users/profile", "GET")).authenticated());
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/posts", "POST")).authenticated());
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/posts/**", "DELETE")).authenticated());
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/posts/**", "PUT")).authenticated());
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/posts/**/**", "DELETE")).authenticated());
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/posts/**/**", "POST")).authenticated());
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/posts/**/**", "PUT")).authenticated());
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return http.build();
    }
}
