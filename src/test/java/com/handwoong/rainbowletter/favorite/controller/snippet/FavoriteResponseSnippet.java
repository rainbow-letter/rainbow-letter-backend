package com.handwoong.rainbowletter.favorite.controller.snippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class FavoriteResponseSnippet {
    public static final Snippet FAVORITE_RESPONSE = responseFields(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("좋아요 ID"),
            fieldWithPath("total").type(JsonFieldType.NUMBER).description("총 좋아요 수"),
            fieldWithPath("dayIncreaseCount").type(JsonFieldType.NUMBER).description("하루동안 증가된 좋아요 수"),
            fieldWithPath("canIncrease").type(JsonFieldType.BOOLEAN).description("오늘 하루 좋아요 가능 여부")
    );

    private FavoriteResponseSnippet() {
    }
}
