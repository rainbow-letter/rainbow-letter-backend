package com.handwoong.rainbowletter.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /**
     * 401 UNAUTHORIZED
     */
    UN_AUTHORIZE(UNAUTHORIZED, "인증에 실패하였습니다."),
    IN_VALID_TOKEN(UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    /**
     * 403 FORBIDDEN
     */
    ACCESS_DENIED(FORBIDDEN, "접근 권한이 없습니다."),

    /**
     * 405 METHOD NOT ALLOWED
     */
    NOT_SUPPORT_METHOD(METHOD_NOT_ALLOWED, "지원하지 않는 요청입니다."),

    /**
     * 500 INTERNAL SERVER ERROR
     */
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버에 문제가 발생하였습니다.");

    private final HttpStatus status;
    private final String message;

    public int statusValue() {
        return status.value();
    }
}
