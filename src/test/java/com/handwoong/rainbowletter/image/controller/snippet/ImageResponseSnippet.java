package com.handwoong.rainbowletter.image.controller.snippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class ImageResponseSnippet {
    public static final Snippet IMAGE_ID_RESPONSE = responseFields(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("이미지 ID")
    );

    private ImageResponseSnippet() {
    }
}
