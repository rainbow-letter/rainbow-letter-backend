package com.handwoong.rainbowletter.common.exception;

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
    INVALID_PATH_VALUE(BAD_REQUEST, "요청 주소에 유효하지 않은 값이 있습니다."),
    METHOD_ARGUMENT_NOT_VALID(BAD_REQUEST, "요청 인자가 잘못되었습니다."),
    REQUIRED_FILE(BAD_REQUEST, "파일을 첨부해주세요."),
    INVALID_FILE_CONTENT_TYPE(BAD_REQUEST, "Content-Type을 명시하였거나, 폼 데이터에 파일이 없습니다."),

    INVALID_EMAIL_FORMAT(BAD_REQUEST, "유효하지 않은 이메일 형식입니다."),
    INVALID_EMAIL(BAD_REQUEST, "존재하지 않는 이메일입니다."),
    EXISTS_EMAIL(BAD_REQUEST, "이미 존재하는 이메일입니다."),

    INVALID_PASSWORD_FORMAT(BAD_REQUEST, "비밀번호는 영문, 숫자를 조합하여 8글자 이상으로 입력해주세요."),
    FAIL_PASSWORD_COMPARE(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    CHECK_EMAIL_AND_PASSWORD(BAD_REQUEST, "이메일 및 비밀번호를 확인 해주세요."),

    INVALID_PHONE_NUMBER_FORMAT(BAD_REQUEST, "유효하지 않은 휴대폰 번호 형식입니다."),
    INVALID_OAUTH_PROVIDER_TYPE(BAD_REQUEST, "유효하지 않은 소셜 로그인 타입입니다."),
    INVALID_MEMBER_STATUS(BAD_REQUEST, "변경 불가능한 회원 상태입니다."),

    MAIL_TEMPLATE_NOT_FOUND(BAD_REQUEST, "이메일 템플릿을 찾을 수 없습니다."),
    FAIL_UPLOAD_IMAGE(BAD_REQUEST, "이미지 업로드에 실패했습니다. 잠시 후 다시 시도해 주세요."),

    INVALID_PERSONALITY_FORMAT(BAD_REQUEST, "유효하지 않은 반려동물 성격 형식입니다."),
    INVALID_FAVORITE_INCREASE(BAD_REQUEST, "하루 최대 좋아요 수를 달성하였습니다."),

    INVALID_SUMMARY_FORMAT(BAD_REQUEST, "제목의 길이는 20자 이하로 입력해주세요."),
    INVALID_CONTENT_FORMAT(BAD_REQUEST, "본문을 입력 해주세요."),

    INVALID_INSPECT_STATUS(BAD_REQUEST, "편지 검수가 먼저 선행되어야 합니다."),
    ALREADY_REPLY(BAD_REQUEST, "이미 답장을 보낸 편지입니다."),

    ILLEGAL_ARGUMENT(BAD_REQUEST, "잘못된 사용자 입력입니다."),

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
    NOT_FOUND_FAVORITE(NOT_FOUND, "해당 좋아요를 찾을 수 없습니다."),
    NOT_FOUND_FAQ(NOT_FOUND, "해당 FAQ를 찾을 수 없습니다."),
    NOT_FOUND_IMAGE(NOT_FOUND, "해당 이미지를 찾을 수 없습니다."),
    NOT_FOUND_PET(NOT_FOUND, "해당 반려 동물을 찾을 수 없습니다."),
    NOT_FOUND_LETTER(NOT_FOUND, "해당 편지를 찾을 수 없습니다."),
    NOT_FOUND_REPLY(NOT_FOUND, "해당 답장을 찾을 수 없습니다."),

    /**
     * 405 METHOD NOT ALLOWED
     */
    NOT_SUPPORT_METHOD(METHOD_NOT_ALLOWED, "지원하지 않는 요청입니다."),

    /**
     * 500 INTERNAL SERVER ERROR
     */
    FAIL_SEND_MAIL(INTERNAL_SERVER_ERROR, "이메일 발송에 실패하였습니다."),
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버에 문제가 발생하였습니다.");

    private final HttpStatus status;
    private final String message;

    public int statusValue() {
        return status.value();
    }
}
