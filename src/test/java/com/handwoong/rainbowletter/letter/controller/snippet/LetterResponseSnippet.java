package com.handwoong.rainbowletter.letter.controller.snippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class LetterResponseSnippet {
    public static final Snippet LETTER_BOX_RESPONSE = responseFields(
            fieldWithPath("letters[].id").type(JsonFieldType.NUMBER)
                    .description("편지 ID"),
            fieldWithPath("letters[].summary").type(JsonFieldType.STRING)
                    .description("편지 제목"),
            fieldWithPath("letters[].status").type(JsonFieldType.STRING)
                    .description("편지 답장 여부 REQUEST || RESPONSE"),
            fieldWithPath("letters[].petName").type(JsonFieldType.STRING)
                    .description("편지를 받는 반려 동물 이름"),
            fieldWithPath("letters[].readStatus").type(JsonFieldType.STRING)
                    .description("답장 읽음 여부 UNREAD || READ")
                    .optional(),
            fieldWithPath("letters[].createdAt").type(JsonFieldType.STRING)
                    .description("편지 작성일")
    );
    public static final Snippet LETTER_RESPONSE = responseFields(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("편지 ID"),
            fieldWithPath("summary").type(JsonFieldType.STRING)
                    .description("편지 제목"),
            fieldWithPath("content").type(JsonFieldType.STRING)
                    .description("편지 본문"),
            fieldWithPath("pet.id").type(JsonFieldType.NUMBER)
                    .description("반려 동물 ID"),
            fieldWithPath("pet.name").type(JsonFieldType.STRING)
                    .description("반려 동물 이름"),
            fieldWithPath("pet.image").type(JsonFieldType.OBJECT)
                    .description("반려 동물 이미지 정보"),
            fieldWithPath("pet.image.id").type(JsonFieldType.NUMBER)
                    .description("반려 동물 이미지 ID")
                    .optional(),
            fieldWithPath("pet.image.objectKey").type(JsonFieldType.STRING)
                    .description("반려 동물 이미지의 ObjectKey")
                    .optional(),
            fieldWithPath("pet.image.url").type(JsonFieldType.STRING)
                    .description("반려 동물 이미지의 URL")
                    .optional(),
            fieldWithPath("image").type(JsonFieldType.OBJECT).
                    description("편지 이미지 정보"),
            fieldWithPath("image.id").type(JsonFieldType.NUMBER)
                    .description("편지 이미지 ID")
                    .optional(),
            fieldWithPath("image.objectKey").type(JsonFieldType.STRING)
                    .description("편지 이미지의 ObjectKey")
                    .optional(),
            fieldWithPath("image.url").type(JsonFieldType.STRING)
                    .description("편지 이미지의 URL")
                    .optional(),
            fieldWithPath("reply.id").type(JsonFieldType.NUMBER)
                    .description("답장 ID")
                    .optional(),
            fieldWithPath("reply.summary").type(JsonFieldType.STRING)
                    .description("답장 제목")
                    .optional(),
            fieldWithPath("reply.content").type(JsonFieldType.STRING)
                    .description("답장 본문")
                    .optional(),
            fieldWithPath("reply.readStatus").type(JsonFieldType.STRING)
                    .description("답장 읽음 여부")
                    .optional(),
            fieldWithPath("reply.type").type(JsonFieldType.STRING)
                    .description("답장 타입 CHAT_GPT || REPLY")
                    .optional(),
            fieldWithPath("createdAt").type(JsonFieldType.STRING)
                    .description("편지 작성일")
    );

    private LetterResponseSnippet() {
    }
}
