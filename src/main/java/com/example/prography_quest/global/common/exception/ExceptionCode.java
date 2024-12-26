package com.example.prography_quest.global.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@ToString
public enum ExceptionCode {
    //API 관련
    OK(200, HttpStatus.OK, "API 요청이 성공했습니다."),
    FAIL(201, HttpStatus.BAD_REQUEST, "불가능한 요청입니다."),
    ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "에러가 발생했습니다."),

    //권한 관련
    UN_AUTHENTICATION(1001, HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    FORBIDDEN(1002, HttpStatus.FORBIDDEN, "권한이 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(1003, HttpStatus.NOT_FOUND, "리프레시 토큰을 찾을 수 없습니다."),
    REFRESH_TOKEN_VALIDATION_FAILED(1004, HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다."),

    //user
    USER_NOT_FOUND(2001, HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(2002, HttpStatus.BAD_REQUEST, "유저가 이미 존재합니다."),
    USER_ALREADY_CERTIFICATION(2003, HttpStatus.BAD_REQUEST, "이미 인증받은 사용자입니다."),
    PASSWORD_NOT_MATCH(2004, HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    //department
    DEPARTMENT_NOT_FOUND(3001, HttpStatus.NOT_FOUND, "학과를 찾을 수 없습니다."),

    //profile
    PROFILE_NOT_FOUND(4001, HttpStatus.NOT_FOUND, "프로필을 찾을 수 없습니다.");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ExceptionCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
