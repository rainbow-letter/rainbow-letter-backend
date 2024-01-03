package com.handwoong.rainbowletter.pet.controller.snippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class PetResponseSnippet {
    public static final Snippet PET_RESPONSES = responseFields(
            fieldWithPath("pets[].id").type(JsonFieldType.NUMBER).description("반려동물 ID"),
            fieldWithPath("pets[].name").type(JsonFieldType.STRING).description("아이의 이름"),
            fieldWithPath("pets[].species").type(JsonFieldType.STRING).description("아이의 종류"),
            fieldWithPath("pets[].owner").type(JsonFieldType.STRING).description("주인을 부르는 호칭"),
            fieldWithPath("pets[].personalities").type(JsonFieldType.ARRAY).description("아이의 성격"),
            fieldWithPath("pets[].deathAnniversary").type(JsonFieldType.STRING).description("아이가 떠난 날").optional(),
            fieldWithPath("pets[].image.id").type(JsonFieldType.NUMBER).description("이미지 ID").optional(),
            fieldWithPath("pets[].image.objectKey").type(JsonFieldType.STRING).description("이미지의 ObjectKey").optional(),
            fieldWithPath("pets[].image.url").type(JsonFieldType.STRING).description("이미지의 URL").optional(),
            fieldWithPath("pets[].favorite.id").type(JsonFieldType.NUMBER).description("좋아요 ID"),
            fieldWithPath("pets[].favorite.total").type(JsonFieldType.NUMBER).description("총 좋아요 수"),
            fieldWithPath("pets[].favorite.dayIncreaseCount").type(JsonFieldType.NUMBER).description("하루동안 증가된 좋아요 수"),
            fieldWithPath("pets[].favorite.canIncrease").type(JsonFieldType.BOOLEAN).description("오늘 하루 좋아요 가능 여부")
    );
    public static final Snippet PET_RESPONSE = responseFields(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("반려동물 ID"),
            fieldWithPath("name").type(JsonFieldType.STRING).description("아이의 이름"),
            fieldWithPath("species").type(JsonFieldType.STRING).description("아이의 종류"),
            fieldWithPath("owner").type(JsonFieldType.STRING).description("주인을 부르는 호칭"),
            fieldWithPath("personalities").type(JsonFieldType.ARRAY).description("아이의 성격"),
            fieldWithPath("deathAnniversary").type(JsonFieldType.STRING).description("아이가 떠난 날").optional(),
            fieldWithPath("image.id").type(JsonFieldType.NUMBER).description("이미지 ID").optional(),
            fieldWithPath("image.objectKey").type(JsonFieldType.STRING).description("이미지의 ObjectKey").optional(),
            fieldWithPath("image.url").type(JsonFieldType.STRING).description("이미지의 URL").optional(),
            fieldWithPath("favorite.id").type(JsonFieldType.NUMBER).description("좋아요 ID"),
            fieldWithPath("favorite.total").type(JsonFieldType.NUMBER).description("총 좋아요 수"),
            fieldWithPath("favorite.dayIncreaseCount").type(JsonFieldType.NUMBER).description("하루동안 증가된 좋아요 수"),
            fieldWithPath("favorite.canIncrease").type(JsonFieldType.BOOLEAN).description("오늘 하루 좋아요 가능 여부")
    );

    private PetResponseSnippet() {
    }
}
