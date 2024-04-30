package com.example.prography_quest.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T>{
    private Integer code;
    private String message;
    private T result;

    public ApiResponse(T response) {
        this.result = response;
    }

    public ApiResponse<T> ok() {
        code = 200;
        message = "API 요청이 성공했습니다.";
        return this;
    }

    public ApiResponse<T> fail() {
        code = 201;
        message = "불가능한 요청입니다.";
        return this;
    }

    public ApiResponse<T> error() {
        code = 500;
        message = "에러가 발생했습니다.";
        return this;
    }
}
