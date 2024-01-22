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
            fieldWithPath("shareLink").type(JsonFieldType.STRING)
                    .description("편지 공유 링크 UUID"),
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
            fieldWithPath("reply.inspection").type(JsonFieldType.BOOLEAN)
                    .description("답장 검수 여부")
                    .optional(),
            fieldWithPath("reply.readStatus").type(JsonFieldType.STRING)
                    .description("답장 읽음 여부")
                    .optional(),
            fieldWithPath("reply.type").type(JsonFieldType.STRING)
                    .description("답장 타입 CHAT_GPT || REPLY")
                    .optional(),
            fieldWithPath("reply.timestamp").type(JsonFieldType.STRING)
                    .description("답장 시간")
                    .optional(),
            fieldWithPath("createdAt").type(JsonFieldType.STRING)
                    .description("편지 작성일")
    );
    public static final Snippet ADMIN_LETTERS = responseFields(
            fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("편지 ID"),
            fieldWithPath("content[].memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
            fieldWithPath("content[].summary").type(JsonFieldType.STRING)
                    .description("편지 제목"),
            fieldWithPath("content[].content").type(JsonFieldType.STRING)
                    .description("편지 본문"),
            fieldWithPath("content[].shareLink").type(JsonFieldType.STRING)
                    .description("편지 공유 링크 UUID"),
            fieldWithPath("content[].pet.id").type(JsonFieldType.NUMBER)
                    .description("반려 동물 ID"),
            fieldWithPath("content[].pet.name").type(JsonFieldType.STRING)
                    .description("반려 동물 이름"),
            fieldWithPath("content[].pet.image").type(JsonFieldType.OBJECT)
                    .description("반려 동물 이미지 정보"),
            fieldWithPath("content[].pet.image.id").type(JsonFieldType.NUMBER)
                    .description("반려 동물 이미지 ID")
                    .optional(),
            fieldWithPath("content[].pet.image.objectKey").type(JsonFieldType.STRING)
                    .description("반려 동물 이미지의 ObjectKey")
                    .optional(),
            fieldWithPath("content[].pet.image.url").type(JsonFieldType.STRING)
                    .description("반려 동물 이미지의 URL")
                    .optional(),
            fieldWithPath("content[].image").type(JsonFieldType.OBJECT).
                    description("편지 이미지 정보"),
            fieldWithPath("content[].image.id").type(JsonFieldType.NUMBER)
                    .description("편지 이미지 ID")
                    .optional(),
            fieldWithPath("content[].image.objectKey").type(JsonFieldType.STRING)
                    .description("편지 이미지의 ObjectKey")
                    .optional(),
            fieldWithPath("content[].image.url").type(JsonFieldType.STRING)
                    .description("편지 이미지의 URL")
                    .optional(),
            fieldWithPath("content[].reply.id").type(JsonFieldType.NUMBER)
                    .description("답장 ID")
                    .optional(),
            fieldWithPath("content[].reply.summary").type(JsonFieldType.STRING)
                    .description("답장 제목")
                    .optional(),
            fieldWithPath("content[].reply.content").type(JsonFieldType.STRING)
                    .description("답장 본문")
                    .optional(),
            fieldWithPath("content[].reply.inspection").type(JsonFieldType.BOOLEAN)
                    .description("답장 검수 여부")
                    .optional(),
            fieldWithPath("content[].reply.inspectionTime").type(JsonFieldType.STRING)
                    .description("답장 검수 시간")
                    .optional(),
            fieldWithPath("content[].reply.readStatus").type(JsonFieldType.STRING)
                    .description("답장 읽음 여부")
                    .optional(),
            fieldWithPath("content[].reply.type").type(JsonFieldType.STRING)
                    .description("답장 타입 CHAT_GPT || REPLY")
                    .optional(),
            fieldWithPath("content[].reply.timestamp").type(JsonFieldType.STRING)
                    .description("답장 시간")
                    .optional(),
            fieldWithPath("content[].reply.chatGptContent").type(JsonFieldType.STRING)
                    .description("ChatGpt 원본")
                    .optional(),
            fieldWithPath("content[].createdAt").type(JsonFieldType.STRING)
                    .description("편지 작성일"),
            fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER)
                    .description("현재 페이지 번호"),
            fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER)
                    .description("현재 페이지 크기"),
            fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("last").type(JsonFieldType.BOOLEAN)
                    .description("마지막 페이지 여부"),
            fieldWithPath("totalPages").type(JsonFieldType.NUMBER)
                    .description("총 페이지 수"),
            fieldWithPath("totalElements").type(JsonFieldType.NUMBER)
                    .description("총 데이터 수"),
            fieldWithPath("size").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("number").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("first").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("")
    );

    private LetterResponseSnippet() {
    }
}
