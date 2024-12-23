package com.example.prography_quest.global.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(servers = {@Server(url = "http://localhost:8080", description = "dev server url")})
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Prography Quest API")
                .version("1.0")
                .description("Prography Quest API");

        return new OpenAPI()
                .components(new Components()
                    .addSecuritySchemes("Bearer 토큰", new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
                )
                .addSecurityItem(new SecurityRequirement().addList("Bearer 토큰"))
                .info(info);
    }
}

// 이 코드는 Swagger(OpenAPI) 문서를 Spring 애플리케이션에서 자동으로 생성하고, JWT Bearer 인증을 사용하도록 설정합니다.
// Info: API의 메타데이터를 설정.
// Components와 SecuritySchemes: Bearer 토큰 기반 인증 스키마를 정의.
// SecurityRequirement: Bearer 토큰 인증을 모든 요청에서 요구.