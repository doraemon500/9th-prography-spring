package com.example.prography_quest.global.common.response;

import com.example.prography_quest.global.common.exception.ExceptionCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) //JSON 직렬화 과정에서 특정 조건에 따라 객체의 필드를 포함하거나 제외하도록 설정하는 어노테이션입니다.
                                           //이 어노테이션은 null 값인 필드를 JSON 출력 결과에서 제외하도록 지정합니다.
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T response;

    public ApiResponse(T response) {
        this.response = response;
    }

    public ApiResponse<T> ok(ExceptionCode exceptionCode) {
        code = exceptionCode.getCode();
        message = exceptionCode.getMessage();
        return this;
    }

    public ApiResponse<T> fail(ExceptionCode exceptionCode) {
        code = exceptionCode.getCode();
        message = exceptionCode.getMessage();
        return this;
    }

    public ApiResponse<T> error(ExceptionCode exceptionCode) {
        code = exceptionCode.getCode();
        message = exceptionCode.getMessage();
        return this;
    }
}

//
//public class ApiResponse {
//    public static void ok(HttpServletResponse response) throws IOException {
//        String result = getResponse(ExceptionCode.OK);
//        response.setContentType("application/json; charset=utf-8");
//        response.setStatus(HttpStatus.OK.value());
//        response.getWriter().println(result);
//    }
//
//    public static void fail(HttpServletResponse response) throws IOException {
//        String result = getResponse(ExceptionCode.FAIL);
//        response.setContentType("application/json; charset=utf-8");
//        response.setStatus(HttpStatus.BAD_REQUEST.value());
//        response.getWriter().println(result);
//    }
//
//    public static void error(HttpServletResponse response) throws IOException {
//        String result = getResponse(ExceptionCode.ERROR);
//        response.setContentType("application/json; charset=utf-8");
//        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        response.getWriter().println(result);
//    }
//
//    private static String getResponse(ExceptionCode exceptionCode) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(exceptionCode);
//    }
//}