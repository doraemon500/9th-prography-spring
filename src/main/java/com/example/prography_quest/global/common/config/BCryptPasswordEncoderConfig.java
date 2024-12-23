package com.example.prography_quest.global.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCryptPasswordEncoderConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

// 작성하신 BCryptPasswordEncoderConfig 클래스는 Spring Framework에서 비밀번호를 암호화하기 위해 **BCryptPasswordEncoder**를 설정하고 빈으로 등록하는 역할을 합니다.
// 이를 통해 애플리케이션 내에서 비밀번호 암호화와 검증을 쉽게 사용할 수 있습니다.