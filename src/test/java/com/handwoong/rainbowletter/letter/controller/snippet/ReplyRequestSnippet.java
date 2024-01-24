package com.handwoong.rainbowletter.letter.controller.snippet;

import static com.handwoong.rainbowletter.util.SnippetAttributeGenerator.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class ReplyRequestSnippet {
    public static final Snippet PARAM_LETTER_ID = pathParameters(parameterWithName("letterId").description("편지 ID"));
    public static final Snippet PARAM_REPLY_ID = pathParameters(parameterWithName("id").description("답장 ID"));
    public static final Snippet SUBMIT_REQUEST = requestFields(
            fieldWithPath("letterId").type(JsonFieldType.NUMBER)
                    .description("편지 ID")
    );
    public static final Snippet UPDATE_REQUEST = requestFields(
            fieldWithPath("summary").type(JsonFieldType.STRING)
                    .description("최종 답장 제목")
                    .attributes(constraints("20자 이내")),
            fieldWithPath("content").type(JsonFieldType.STRING)
                    .description("최종 답장 본문")
                    .attributes(constraints("1000자 이내"))
    );

    private ReplyRequestSnippet() {
    }
}
