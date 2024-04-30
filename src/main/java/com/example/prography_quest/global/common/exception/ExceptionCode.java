package com.example.prography_quest.global.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@ToString
public enum ExceptionCode {
    UN_AUTHENTICATION(1001, HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    FORBIDDEN(1002, HttpStatus.FORBIDDEN, "권한이 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(1003, HttpStatus.NOT_FOUND, "리프레시 토큰을 찾을 수 없습니다."),
    REFRESH_TOKEN_VALIDATION_FAILED(1004, HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다."),

    USER_NOT_FOUND(2001, HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(2002, HttpStatus.BAD_REQUEST, "유저가 이미 존재합니다."),
    USER_ALREADY_CERTIFICATION(2003, HttpStatus.BAD_REQUEST, "이미 인증받은 사용자입니다.");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ExceptionCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;


    }
}
