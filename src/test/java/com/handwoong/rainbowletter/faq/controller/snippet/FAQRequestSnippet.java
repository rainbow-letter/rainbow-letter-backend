package com.handwoong.rainbowletter.faq.controller.snippet;

import static com.handwoong.rainbowletter.util.SnippetAttributeGenerator.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class FAQRequestSnippet {
    public static final Snippet CREATE_REQUEST = requestFields(
            fieldWithPath("summary").type(JsonFieldType.STRING)
                    .description("제목").attributes(constraints("30자 이하")),
            fieldWithPath("detail").type(JsonFieldType.STRING)
                    .description("본문").attributes(constraints("공백만 불가"))
    );
    public static final Snippet PATH_PARAM_ID = pathParameters(
            parameterWithName("id").description("FAQ ID")
    );
    public static final Snippet CHANGE_SEQUENCE_REQUEST = requestFields(
            fieldWithPath("targetId").type(JsonFieldType.NUMBER).description("순서를 변경할 대상의 ID")
    );
    public static final Snippet UPDATE_REQUEST = requestFields(
            fieldWithPath("summary").type(JsonFieldType.STRING)
                    .description("제목").attributes(constraints("30자 이하")),
            fieldWithPath("detail").type(JsonFieldType.STRING)
                    .description("본문").attributes(constraints("공백만 불가"))
    );

    private FAQRequestSnippet() {
    }
}
