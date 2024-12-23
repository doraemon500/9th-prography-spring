package com.example.prography_quest.global.common.config;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    CorsConfigurationSource  configurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true); // CORS(Cross-Origin Resource Sharing) 설정 시 자격 증명(쿠키, 인증 헤더 등)을 포함한 요청을 허용할지 여부
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "DELETE", "PUT"));
        corsConfig.setAllowedHeaders(List.of("Authorization")); // 클라이언트에서 요청 시 포함할 수 있는 허용된 HTTP 요청 헤더를 설정
                                                                    // 이 설정이 없으면 브라우저는 기본적으로 CORS 요청에서 민감한 헤더(예: Authorization)를 차단할 수 있습니다.
        corsConfig.addExposedHeader("Authorization"); //  브라우저가 CORS 응답에서 읽을 수 있도록 허용된 헤더를 설정합니다.
		                                              //  기본적으로 브라우저는 민감한 응답 헤더를 접근 불가능하게 차단합니다.
		                                              //  addExposedHeader를 통해 특정 헤더를 브라우저가 접근할 수 있도록 노출합니다.
//        corsConfig.addAllowedOriginPattern("*");
//        corsConfig.addAllowedHeader("*");
//        corsConfig.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}
