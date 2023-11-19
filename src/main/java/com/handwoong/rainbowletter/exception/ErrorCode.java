package com.handwoong.rainbowletter.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /**
     * 400 BAD REQUEST
     */
    EXISTS_EMAIL(BAD_REQUEST, "이미 존재하는 이메일입니다."),
    INVALID_EMAIL(BAD_REQUEST, "존재하지 않는 이메일입니다."),
    METHOD_ARGUMENT_NOT_VALID(BAD_REQUEST, "요청 인자가 잘못되었습니다."),
    CHECK_EMAIL_AND_PASSWORD(BAD_REQUEST, "이메일 및 비밀번호를 확인 해주세요."),
    INVALID_MEMBER_STATUS(BAD_REQUEST, "변경 불가능한 회원 상태입니다."),

    /**
     * 401 UNAUTHORIZED
     */
    UN_AUTHORIZE(UNAUTHORIZED, "인증에 실패하였습니다."),
    INVALID_TOKEN(UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_MEMBER(UNAUTHORIZED, "계정이 휴면 상태입니다."),
    NEED_VERIFY_EMAIL(UNAUTHORIZED, "이메일 인증이 완료되지 않았습니다."),
    LOCKED_MEMBER(UNAUTHORIZED, "계정이 잠금 상태입니다."),
    LEAVE_MEMBER(UNAUTHORIZED, "탈퇴된 계정입니다."),

    /**
     * 403 FORBIDDEN
     */
    ACCESS_DENIED(FORBIDDEN, "접근 권한이 없습니다."),

    /**
     * 404 NOT FOUND
     */
    NOT_FOUND_MEMBER(NOT_FOUND, "해당 회원을 찾을 수 없습니다."),

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
