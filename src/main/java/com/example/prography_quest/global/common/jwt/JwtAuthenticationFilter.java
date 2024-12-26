package com.example.prography_quest.global.common.jwt;

import com.example.prography_quest.domain.auth.domain.Auth;
import com.example.prography_quest.global.common.response.SecurityResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.Duration;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Auth auth = objectMapper.readValue(request.getInputStream(), Auth.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword());
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityResponse.unAuthentication(response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Auth auth = (Auth) authResult.getPrincipal();
        String jwtToken = jwtProvider.createAccessToken(auth);
        String refreshToken = jwtProvider.createRefreshToken(auth);
        response.addHeader(HttpHeaders.AUTHORIZATION, jwtToken);
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(Duration.ofDays(14))
                .path("/")
//                .secure(true) //Https 사용시 true
                .sameSite("None") //csrf 공격을 방지하기 위해서
//                .domain("localhost:3000") // 도메인이 다르면 쿠키를 못받음.
                .httpOnly(true)
                .build();
        response.setHeader("Set-Cookie", cookie.toString());
    }
}
