package com.handwoong.rainbowletter.faq.controller;

import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.faq.controller.snippet.FAQRequestSnippet.CHANGE_SEQUENCE_REQUEST;
import static com.handwoong.rainbowletter.faq.controller.snippet.FAQRequestSnippet.CREATE_REQUEST;
import static com.handwoong.rainbowletter.faq.controller.snippet.FAQRequestSnippet.PATH_PARAM_ID;
import static com.handwoong.rainbowletter.faq.controller.snippet.FAQRequestSnippet.UPDATE_REQUEST;
import static com.handwoong.rainbowletter.faq.controller.snippet.FAQResponseSnippet.ADMIN_FAQS_LIST;
import static com.handwoong.rainbowletter.faq.controller.snippet.FAQResponseSnippet.USER_FAQS_LIST;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.ADMIN_AUTHORIZATION_HEADER;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.USER_AUTHORIZATION_HEADER;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getFilter;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.faq.controller.request.FAQChangeSequenceRequest;
import com.handwoong.rainbowletter.faq.controller.request.FAQCreateRequest;
import com.handwoong.rainbowletter.faq.controller.request.FAQUpdateRequest;
import com.handwoong.rainbowletter.faq.controller.response.FAQAdminResponses;
import com.handwoong.rainbowletter.faq.controller.response.FAQUserResponses;
import com.handwoong.rainbowletter.util.ControllerTestSupporter;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/member.sql", "classpath:sql/faq.sql"})
class FAQControllerTest extends ControllerTestSupporter {
    @Test
    void FAQ_목록_일반_사용자_조회() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = listByUser(token);
        final FAQUserResponses result = response.body().as(FAQUserResponses.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(result.faqs()).hasSize(2);

        assertThat(result.faqs().get(0).id()).isEqualTo(1);
        assertThat(result.faqs().get(0).summary()).isEqualTo("무지개 편지는 무슨 서비스인가요?");
        assertThat(result.faqs().get(0).detail()).isEqualTo("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.");

        assertThat(result.faqs().get(1).id()).isEqualTo(2);
        assertThat(result.faqs().get(1).summary()).isEqualTo("답장이 온 건 어떻게 알 수 있나요?");
        assertThat(result.faqs().get(1).detail()).isEqualTo("답장이 도착하면 등록하신 이메일 주소로 메일을 보내드려요.");
    }

    private ExtractableResponse<Response> listByUser(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(USER_AUTHORIZATION_HEADER, USER_FAQS_LIST))
                .when().get("/api/faqs/list")
                .then().log().all().extract();
    }

    @Test
    void FAQ_목록_관리자_조회() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = listByAdmin(token);
        final FAQAdminResponses result = response.body().as(FAQAdminResponses.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(result.faqs()).hasSize(3);

        assertThat(result.faqs().get(0).id()).isEqualTo(1);
        assertThat(result.faqs().get(0).summary()).isEqualTo("무지개 편지는 무슨 서비스인가요?");
        assertThat(result.faqs().get(0).detail()).isEqualTo("무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.");
        assertThat(result.faqs().get(0).visibility()).isTrue();

        assertThat(result.faqs().get(1).id()).isEqualTo(2);
        assertThat(result.faqs().get(1).summary()).isEqualTo("답장이 온 건 어떻게 알 수 있나요?");
        assertThat(result.faqs().get(1).detail()).isEqualTo("답장이 도착하면 등록하신 이메일 주소로 메일을 보내드려요.");
        assertThat(result.faqs().get(1).visibility()).isTrue();

        assertThat(result.faqs().get(2).id()).isEqualTo(3);
        assertThat(result.faqs().get(2).summary()).isEqualTo("답장은 언제 오나요?");
        assertThat(result.faqs().get(2).detail()).isEqualTo("편지를 보내고 1~2일 이내 도착해요.");
        assertThat(result.faqs().get(2).visibility()).isFalse();
    }

    private ExtractableResponse<Response> listByAdmin(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, ADMIN_FAQS_LIST))
                .when().get("/api/faqs/list/admin")
                .then().log().all().extract();
    }

    @Test
    void FAQ_생성() {
        // given
        final String token = adminAccessToken;
        final FAQCreateRequest request = new FAQCreateRequest(
                "휴대폰 번호는 어디에 사용되나요?", "휴대폰 번호를 등록하시면 답장이 도착했을때 문자 메시지를 보내드려요.");

        // when
        final ExtractableResponse<Response> response = create(request, token);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    private ExtractableResponse<Response> create(final FAQCreateRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, CREATE_REQUEST))
                .when().post("/api/faqs")
                .then().log().all().extract();
    }

    @Test
    void FAQ_사용자에게_보이기_여부_변경() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = changeVisibility(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> changeVisibility(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, PATH_PARAM_ID))
                .when().put("/api/faqs/visibility/{id}", 1L)
                .then().log().all().extract();
    }

    @Test
    void FAQ_순서_변경() {
        // given
        final String token = adminAccessToken;
        final FAQChangeSequenceRequest request = new FAQChangeSequenceRequest(3L);

        // when
        final ExtractableResponse<Response> response = changeSequence(request, token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> changeSequence(final FAQChangeSequenceRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, PATH_PARAM_ID, CHANGE_SEQUENCE_REQUEST))
                .when().put("/api/faqs/sequence/{id}", 1L)
                .then().log().all().extract();
    }

    @Test
    void FAQ_업데이트() {
        // given
        final String token = adminAccessToken;
        final FAQUpdateRequest request = new FAQUpdateRequest("문의는 어떻게 드리면 되나요?", "swcteam41@gmail.com으로 메일을 보내주세요.");

        // when
        final ExtractableResponse<Response> response = update(request, token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> update(final FAQUpdateRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, PATH_PARAM_ID, UPDATE_REQUEST))
                .when().put("/api/faqs/{id}", 1L)
                .then().log().all().extract();
    }

    @Test
    void FAQ_삭제() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = delete(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> delete(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, PATH_PARAM_ID))
                .when().delete("/api/faqs/{id}", 1L)
                .then().log().all().extract();
    }
}
