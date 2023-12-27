package com.handwoong.rainbowletter.domain.favorite.controller;

import static com.handwoong.rainbowletter.config.security.jwt.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.config.security.jwt.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.domain.ControllerTestProvider;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/member.sql", "classpath:sql/pet.sql"})
class FavoriteControllerTest extends ControllerTestProvider {
    @Test
    @DisplayName("좋아요를 증가시킨다.")
    void increase_favorite() {
        // given
        // when
        final ExtractableResponse<Response> response = increase(userAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> increase(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .when().post("/api/favorite/1")
                .then().log().all().extract();
    }
}