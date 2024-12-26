package com.example.prography_quest.domain.user.controller;

import com.example.prography_quest.domain.user.domain.User;
import com.example.prography_quest.domain.user.dto.UserResponse;
import com.example.prography_quest.domain.user.service.UserService;
import com.example.prography_quest.global.common.exception.ExceptionCode;
import com.example.prography_quest.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User", description = "user 관련 작업 API")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin
    @Operation(summary = "저장된 유저들 지정된 만큼 반환 api")
    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getUsers(
            @Positive @RequestParam(name = "size") int size,
            @Positive @RequestParam(name = "page") int page) {

        Page<User> userPage = userService.findUsers(page, size);
        UserResponse userResponse = UserResponse.builder()
                .totalElements((int) userPage.getTotalElements())
                .totalPages((int) userPage.getTotalPages())
                .userList(userPage.getContent())
                .build();
        return ResponseEntity.ok().body(new ApiResponse<UserResponse>(userResponse).ok(ExceptionCode.OK));
    }
}
