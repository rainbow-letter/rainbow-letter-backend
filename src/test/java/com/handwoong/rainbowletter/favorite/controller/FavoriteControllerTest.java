package com.handwoong.rainbowletter.favorite.controller;

import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.favorite.controller.snippet.FavoriteRequestSnippet.PATH_PARAM_ID;
import static com.handwoong.rainbowletter.favorite.controller.snippet.FavoriteResponseSnippet.FAVORITE_RESPONSE;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getFilter;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.favorite.controller.response.FavoriteResponse;
import com.handwoong.rainbowletter.favorite.domain.Favorite;
import com.handwoong.rainbowletter.favorite.service.port.FavoriteRepository;
import com.handwoong.rainbowletter.util.ControllerTestSupporter;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

class FavoriteControllerTest extends ControllerTestSupporter {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @BeforeEach
    void initFavorite() {
        favoriteRepository.save(Favorite.create());
    }

    @Test
    void 좋아요_증가() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = increase(token);
        final FavoriteResponse result = response.body().as(FavoriteResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(result.id()).isEqualTo(1);
        assertThat(result.total()).isEqualTo(1);
        assertThat(result.dayIncreaseCount()).isEqualTo(1);
        assertThat(result.canIncrease()).isTrue();
    }

    private ExtractableResponse<Response> increase(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(AUTHORIZATION_HEADER, PATH_PARAM_ID, FAVORITE_RESPONSE))
                .when().post("/api/favorite/{id}", 1)
                .then().log().all().extract();
    }
}
