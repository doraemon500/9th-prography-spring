package com.example.prography_quest.global.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.prography_quest.domain.auth.domain.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("{jwt.access.secret}")
    private String accessTokenSecretKey;
    @Value("${jwt.refresh.secret")
    private String refreshTokenSecretKey;

    private static final long ACCESS_TOKEN_EXPIRATION_TIME = Duration.ofDays(14).toMillis();
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = Duration.ofDays(30).toMillis();
    public static final String PREFIX = "Bearer ";

    public String createAccessToken(Auth auth) {
        String accessToken = JWT.create().withSubject("jwt").withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .withClaim("id", auth.getId())
                .sign(Algorithm.HMAC512(accessTokenSecretKey));
        return PREFIX + accessToken;
    }

    public String createTestAccessToken(Long authId) {
        String accessToken = JWT.create().withSubject("jwt").withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .withClaim("id", authId)
                .sign(Algorithm.HMAC512(accessTokenSecretKey));
        return PREFIX + accessToken;
    }

    public String createRefreshToken(Auth auth) {
        return JWT.create().withSubject("jwt").withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .withClaim("id", auth.getId())
                .sign(Algorithm.HMAC512(refreshTokenSecretKey));
    }

    public Auth accessTokenVerify(String accessToken) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(accessTokenSecretKey)).build().verify(accessToken);
        Long id = decodedJWT.getClaim("id").asLong();
        return Auth.builder()
                .id(id)
                .build();
    }

    public Auth refreshTokenVerify(String refreshToken) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(refreshTokenSecretKey)).build().verify(refreshToken);
        Long id = decodedJWT.getClaim("id").asLong();
        return Auth.builder()
                .id(id)
                .build();
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            JWT.require(Algorithm.HMAC512(accessTokenSecretKey)).build().verify(accessToken);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            JWT.require(Algorithm.HMAC512(refreshTokenSecretKey)).build().verify(refreshToken);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
