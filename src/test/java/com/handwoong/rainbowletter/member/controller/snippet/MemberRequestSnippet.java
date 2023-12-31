package com.handwoong.rainbowletter.member.controller.snippet;

import static com.handwoong.rainbowletter.util.SnippetAttributeGenerator.constraints;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class MemberRequestSnippet {
    public static final Snippet AUTHORIZATION_HEADER = requestHeaders(
            headerWithName("Authorization").description("액세스 토큰")
    );
    public static final Snippet USER_AUTHORIZATION_HEADER = requestHeaders(
            headerWithName("Authorization").description("사용자 권한 액세스 토큰")
    );
    public static final Snippet ADMIN_AUTHORIZATION_HEADER = requestHeaders(
            headerWithName("Authorization").description("관리자 권한 액세스 토큰")
    );
    public static final Snippet LOGIN_REQUEST = requestFields(
            fieldWithPath("email").type(JsonFieldType.STRING)
                    .description("이메일").attributes(constraints("이메일 형식")),
            fieldWithPath("password").type(JsonFieldType.STRING)
                    .description("비밀번호").attributes(constraints("영문, 숫자 조합 8글자 이상"))
    );
    public static final Snippet FIND_PASSWORD_REQUEST = requestFields(
            fieldWithPath("email").type(JsonFieldType.STRING)
                    .description("이메일").attributes(constraints("이메일 형식"))
    );

    public static final Snippet REGISTER_REQUEST = requestFields(
            fieldWithPath("email").type(JsonFieldType.STRING)
                    .description("이메일").attributes(constraints("이메일 형식")),
            fieldWithPath("password").type(JsonFieldType.STRING)
                    .description("비밀번호").attributes(constraints("영문, 숫자 조합 8글자 이상"))
    );
    public static final Snippet CHANGE_PASSWORD_REQUEST = requestFields(
            fieldWithPath("password").type(JsonFieldType.STRING)
                    .description("기존 비밀번호").attributes(constraints("영문, 숫자 조합 8글자 이상")),
            fieldWithPath("newPassword").type(JsonFieldType.STRING)
                    .description("새 비밀번호").attributes(constraints("영문, 숫자 조합 8글자 이상"))
    );
    public static final Snippet RESET_PASSWORD_REQUEST = requestFields(
            fieldWithPath("newPassword").type(JsonFieldType.STRING)
                    .description("새 비밀번호").attributes(constraints("영문, 숫자 조합 8글자 이상"))
    );
    public static final Snippet CHANGE_PHONE_NUMBER_REQUEST = requestFields(
            fieldWithPath("phoneNumber").type(JsonFieldType.STRING)
                    .description("새 휴대폰 번호").attributes(constraints("01로 시작하는 7~8자리 문자열 ex) 01011112222"))
    );

    private MemberRequestSnippet() {
    }
}
