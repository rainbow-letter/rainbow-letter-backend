package com.handwoong.rainbowletter.member.controller.snippet;

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
    public static final Snippet LOGIN_REQUEST = requestFields(
            fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
            fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
    );
    public static final Snippet FIND_PASSWORD_REQUEST = requestFields(
            fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
    );

    public static final Snippet REGISTER_REQUEST = requestFields(
            fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
            fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
    );
    public static final Snippet CHANGE_PASSWORD_REQUEST = requestFields(
            fieldWithPath("password").type(JsonFieldType.STRING).description("기존 비밀번호"),
            fieldWithPath("newPassword").type(JsonFieldType.STRING).description("새 비밀번호")
    );
    public static final Snippet RESET_PASSWORD_REQUEST = requestFields(
            fieldWithPath("newPassword").type(JsonFieldType.STRING).description("새 비밀번호")
    );
    public static final Snippet CHANGE_PHONE_NUMBER_REQUEST = requestFields(
            fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("새 휴대폰 번호")
    );

    private MemberRequestSnippet() {
    }
}
