package com.handwoong.rainbowletter.letter.controller;

import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.letter.controller.snippet.ReplyRequestSnippet.PARAM_LETTER_ID;
import static com.handwoong.rainbowletter.letter.controller.snippet.ReplyRequestSnippet.PARAM_REPLY_ID;
import static com.handwoong.rainbowletter.letter.controller.snippet.ReplyRequestSnippet.SUBMIT_REQUEST;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.ADMIN_AUTHORIZATION_HEADER;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getFilter;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.letter.controller.request.ReplySubmitRequest;
import com.handwoong.rainbowletter.util.ControllerTestSupporter;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/member.sql", "classpath:sql/pet.sql", "classpath:sql/letter.sql"})
class ReplyControllerTest extends ControllerTestSupporter {
    @Test
    void 답장_재생성() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = generate(token);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    private ExtractableResponse<Response> generate(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, PARAM_LETTER_ID))
                .when().post("/api/replies/admin/generate/{letterId}", 1)
                .then().log().all().extract();
    }

    @Test
    void 답장_보내기() {
        // given
        final String token = adminAccessToken;
        final ReplySubmitRequest request = new ReplySubmitRequest(1L, "답장 최종 제목", "답장 최종 본문");

        // when
        final ExtractableResponse<Response> response = submit(request, token);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    private ExtractableResponse<Response> submit(final ReplySubmitRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, PARAM_REPLY_ID, SUBMIT_REQUEST))
                .when().post("/api/replies/admin/submit/{id}", 2)
                .then().log().all().extract();
    }

    @Test
    void 답장_읽기() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = read(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> read(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(AUTHORIZATION_HEADER, PARAM_REPLY_ID))
                .when().post("/api/replies/read/{id}", 2)
                .then().log().all().extract();
    }
}
