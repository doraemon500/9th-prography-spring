package com.example.prography_quest.domain.health.controller;

import com.example.prography_quest.global.common.exception.ExceptionCode;
import com.example.prography_quest.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health", description = "헬스체크 API")
@RequiredArgsConstructor
public class HealthController {

    @CrossOrigin
    @Operation(summary = "헬스 체크")
    @GetMapping("/health")
    public ResponseEntity<ApiResponse> getHealth() {
        return ResponseEntity.ok().body(new ApiResponse<Void>().ok(ExceptionCode.OK));
    }
}
