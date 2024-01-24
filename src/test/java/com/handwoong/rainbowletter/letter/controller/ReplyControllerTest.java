package com.handwoong.rainbowletter.letter.controller;

import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.letter.controller.snippet.ReplyRequestSnippet.PARAM_LETTER_ID;
import static com.handwoong.rainbowletter.letter.controller.snippet.ReplyRequestSnippet.PARAM_REPLY_ID;
import static com.handwoong.rainbowletter.letter.controller.snippet.ReplyRequestSnippet.SUBMIT_REQUEST;
import static com.handwoong.rainbowletter.letter.controller.snippet.ReplyRequestSnippet.UPDATE_REQUEST;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.ADMIN_AUTHORIZATION_HEADER;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getFilter;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.letter.controller.request.ReplySubmitRequest;
import com.handwoong.rainbowletter.letter.controller.request.ReplyUpdateRequest;
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
        final ReplySubmitRequest request = new ReplySubmitRequest(1L);

        // when
        final ExtractableResponse<Response> response = submit(request, token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> submit(final ReplySubmitRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, PARAM_REPLY_ID, SUBMIT_REQUEST))
                .when().post("/api/replies/admin/submit/{id}", 3)
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

    @Test
    void 답장_검수() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = inspect(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> inspect(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, PARAM_REPLY_ID))
                .when().post("/api/replies/admin/inspect/{id}", 2)
                .then().log().all().extract();
    }

    @Test
    void 답장_수정() {
        // given
        final String token = adminAccessToken;
        final ReplyUpdateRequest request = new ReplyUpdateRequest("수정 제목", "수정 본문");

        // when
        final ExtractableResponse<Response> response = update(request, token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> update(final ReplyUpdateRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, PARAM_REPLY_ID, UPDATE_REQUEST))
                .when().put("/api/replies/admin/{id}", 2)
                .then().log().all().extract();
    }
}
