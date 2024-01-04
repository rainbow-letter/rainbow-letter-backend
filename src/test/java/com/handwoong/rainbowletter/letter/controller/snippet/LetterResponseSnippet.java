package com.handwoong.rainbowletter.letter.controller.snippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class LetterResponseSnippet {
    public static final Snippet LETTER_BOX_RESPONSE = responseFields(
            fieldWithPath("letters[].id").type(JsonFieldType.NUMBER).description("편지 ID"),
            fieldWithPath("letters[].summary").type(JsonFieldType.STRING).description("편지 제목"),
            fieldWithPath("letters[].status").type(JsonFieldType.STRING).description("편지 답장 여부 REQUEST || RESPONSE"),
            fieldWithPath("letters[].petName").type(JsonFieldType.STRING).description("편지를 받는 반려 동물 이름"),
            fieldWithPath("letters[].createdAt").type(JsonFieldType.STRING).description("편지 작성일")
    );

    private LetterResponseSnippet() {
    }
}
