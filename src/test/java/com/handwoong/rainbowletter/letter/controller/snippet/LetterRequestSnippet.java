package com.handwoong.rainbowletter.letter.controller.snippet;

import static com.handwoong.rainbowletter.util.SnippetAttributeGenerator.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class LetterRequestSnippet {
    public static final Snippet PARAM_PET_ID = queryParameters(parameterWithName("pet").description("반려 동물 ID"));
    public static final Snippet PARAM_LETTER_ID = pathParameters(parameterWithName("id").description("편지 ID"));
    public static final Snippet PARAM_SHARE_LINK = pathParameters(
            parameterWithName("shareLink").description("편지 공유 UUID"));
    public static final Snippet CREATE_REQUEST = requestFields(
            fieldWithPath("summary").type(JsonFieldType.STRING)
                    .description("편지 제목")
                    .attributes(constraints("20자 이내")),
            fieldWithPath("content").type(JsonFieldType.STRING)
                    .description("편지 본문")
                    .attributes(constraints("1000자 이내")),
            fieldWithPath("image").type(JsonFieldType.NUMBER)
                    .description("편지에 들어간 이미지 ID")
                    .optional()
    );
    public static final Snippet ADMIN_LETTERS_QUERY_PARAMETERS = queryParameters(
            parameterWithName("type").description("답장 발송 여부").attributes(constraints("ALL | WAIT | COMPLETE 대문자")),
            parameterWithName("startDate").description("검색 시작 날짜").attributes(constraints("yyyy-MM-dd")),
            parameterWithName("endDate").description("검색 종료 날짜").attributes(constraints("yyyy-MM-dd")),
            parameterWithName("email").description("검색 이메일").attributes(constraints("검색 이메일 기본값으로 공백 넣어주세요.")),
            parameterWithName("page").description("페이지 번호").attributes(constraints("0부터 시작")),
            parameterWithName("size").description("페이지 데이터 조회 개수")
    );

    private LetterRequestSnippet() {
    }
}
