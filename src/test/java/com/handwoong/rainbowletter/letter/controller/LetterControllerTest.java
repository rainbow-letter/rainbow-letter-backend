package com.handwoong.rainbowletter.letter.controller;

import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.letter.controller.snippet.LetterRequestSnippet.CREATE_REQUEST;
import static com.handwoong.rainbowletter.letter.controller.snippet.LetterRequestSnippet.PARAM_LETTER_ID;
import static com.handwoong.rainbowletter.letter.controller.snippet.LetterRequestSnippet.PARAM_PET_ID;
import static com.handwoong.rainbowletter.letter.controller.snippet.LetterResponseSnippet.LETTER_BOX_RESPONSE;
import static com.handwoong.rainbowletter.letter.controller.snippet.LetterResponseSnippet.LETTER_RESPONSE;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getFilter;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.letter.controller.request.LetterCreateRequest;
import com.handwoong.rainbowletter.util.ControllerTestSupporter;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/member.sql", "classpath:sql/pet.sql", "classpath:sql/letter.sql"})
class LetterControllerTest extends ControllerTestSupporter {
    @Test
    void 편지_생성() {
        // given
        final String token = userAccessToken;
        final LetterCreateRequest request =
                new LetterCreateRequest("콩아 잘 지내고 있니?", "콩아 잘 지내고 있니? 너를 보낸지 얼마 안되었는데 벌써 너가 보고싶다.", 3L);

        // when
        final ExtractableResponse<Response> response = create(request, token);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    private ExtractableResponse<Response> create(final LetterCreateRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .queryParam("pet", 1)
                .filter(getFilter().document(AUTHORIZATION_HEADER, PARAM_PET_ID, CREATE_REQUEST))
                .when().post("/api/letters")
                .then().log().all().extract();
    }

    @Test
    void 편지_목록_조회() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = findAllLetterBoxByEmail(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> findAllLetterBoxByEmail(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(AUTHORIZATION_HEADER, LETTER_BOX_RESPONSE))
                .when().get("/api/letters/list")
                .then().log().all().extract();
    }

    @Test
    void 편지_단건_조회() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = findOne(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> findOne(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(AUTHORIZATION_HEADER, PARAM_LETTER_ID, LETTER_RESPONSE))
                .when().get("/api/letters/{id}", 1)
                .then().log().all().extract();
    }
}
