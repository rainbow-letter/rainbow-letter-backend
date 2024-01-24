package com.handwoong.rainbowletter.faq.controller.snippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class FAQResponseSnippet {
    public static final Snippet USER_FAQS_LIST = responseFields(
            fieldWithPath("faqs[].id").type(JsonFieldType.NUMBER).description("FAQ ID"),
            fieldWithPath("faqs[].summary").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("faqs[].detail").type(JsonFieldType.STRING).description("상세 내용")
    );
    public static final Snippet ADMIN_FAQS_LIST = responseFields(
            fieldWithPath("faqs[].id").type(JsonFieldType.NUMBER).description("FAQ ID"),
            fieldWithPath("faqs[].summary").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("faqs[].detail").type(JsonFieldType.STRING).description("상세 내용"),
            fieldWithPath("faqs[].visibility").type(JsonFieldType.BOOLEAN).description("사용자에게 보이기 여부")
    );

    private FAQResponseSnippet() {
    }
}
