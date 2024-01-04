package com.handwoong.rainbowletter.letter.controller.snippet;

import static com.handwoong.rainbowletter.util.SnippetAttributeGenerator.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class LetterRequestSnippet {
    public static final Snippet PARAM_PET_ID = queryParameters(parameterWithName("pet").description("반려 동물 ID"));
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

    private LetterRequestSnippet() {
    }
}
