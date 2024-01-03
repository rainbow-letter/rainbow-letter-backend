package com.handwoong.rainbowletter.member.controller.snippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class MemberResponseSnippet {
    public static final Snippet INFO_RESPONSE = responseFields(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 ID"),
            fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
            fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("휴대폰 번호").optional(),
            fieldWithPath("role").type(JsonFieldType.STRING).description("권한 정보")
    );
    public static final Snippet LOGIN_RESPONSE = responseFields(
            fieldWithPath("grantType").type(JsonFieldType.STRING).description("토큰 타입"),
            fieldWithPath("token").type(JsonFieldType.STRING).description("액세스 토큰")
    );

    public static final Snippet REGISTER_RESPONSE = responseFields(
            fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
    );

    private MemberResponseSnippet() {
    }
}
