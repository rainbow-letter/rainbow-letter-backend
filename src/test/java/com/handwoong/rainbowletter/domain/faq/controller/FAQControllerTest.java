package com.handwoong.rainbowletter.domain.faq.controller;

import static com.handwoong.rainbowletter.common.config.security.jwt.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.common.config.security.jwt.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.common.exception.ErrorResponse;
import com.handwoong.rainbowletter.domain.ControllerTestProvider;
import com.handwoong.rainbowletter.faq.controller.response.FAQAdminResponse;
import com.handwoong.rainbowletter.faq.controller.response.FAQResponse;
import com.handwoong.rainbowletter.faq.domain.dto.FAQChangeSequenceRequest;
import com.handwoong.rainbowletter.faq.domain.dto.FAQRequest;
import com.handwoong.rainbowletter.faq.infrastructure.FAQ;
import com.handwoong.rainbowletter.faq.infrastructure.FAQRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class FAQControllerTest extends ControllerTestProvider {
    @Autowired
    private FAQRepository faqRepository;

    @Test
    @DisplayName("FAQ 목록을 조회한다.")
    void find_faqs() {
        // given
        final FAQRequest request = new FAQRequest("제목", "본문");
        final FAQ faq = FAQ.create(request);
        faqRepository.save(faq);

        // when
        final ExtractableResponse<Response> response = findAllFAQs(userAccessToken);
        final FAQResponse faqResponse = response.body().as(FAQResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(faqResponse.faqs()).hasSize(1);
    }

    @Test
    @DisplayName("관리자 FAQ 목록을 조회한다.")
    void find_admin_faqs() {
        // given
        final FAQRequest request = new FAQRequest("제목", "본문");
        final FAQ faq = FAQ.create(request);
        faqRepository.save(faq);

        // when
        final ExtractableResponse<Response> response = findAllAdminFAQs(adminAccessToken);
        final FAQAdminResponse faqResponse = response.body().as(FAQAdminResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(faqResponse.faqs()).hasSize(1);
        assertThat(faqResponse.faqs().get(0)).extracting("visibility").isEqualTo(true);
    }

    @Test
    @DisplayName("FAQ를 생성한다.")
    void create_faq() {
        // given
        final FAQRequest request = new FAQRequest("제목", "본문");

        // when
        final ExtractableResponse<Response> response = create(request, adminAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("관리자가 아니라면 FAQ 생성에 실패한다.")
    void invalid_role_create_faq() {
        // given
        final FAQRequest request = new FAQRequest("제목", "본문");

        // when
        final ExtractableResponse<Response> response = create(request, userAccessToken);
        final ErrorResponse errorResponse = response.body().as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(errorResponse.message()).isEqualTo("접근 권한이 없습니다.");
    }

    @Test
    @DisplayName("FAQ를 수정한다.")
    void edit_faq() {
        // given
        final FAQRequest request = new FAQRequest("제목", "본문");
        final FAQ faq = FAQ.create(request);
        faqRepository.save(faq);

        final FAQRequest editRequest = new FAQRequest("제목 수정", "본문 수정");

        // when
        final ExtractableResponse<Response> response = edit(faq.getId(), editRequest, adminAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("관리자가 아니라면 FAQ 수정에 실패한다.")
    void invalid_role_edit_faq() {
        // given
        final FAQRequest request = new FAQRequest("제목", "본문");
        final FAQ faq = FAQ.create(request);
        faqRepository.save(faq);

        final FAQRequest editRequest = new FAQRequest("제목 수정", "본문 수정");

        // when
        final ExtractableResponse<Response> response = edit(faq.getId(), editRequest, userAccessToken);
        final ErrorResponse errorResponse = response.body().as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(errorResponse.message()).isEqualTo("접근 권한이 없습니다.");
    }

    @Test
    @DisplayName("FAQ 보이기 여부를 수정한다.")
    void change_visibility_faq() {
        // given
        final FAQRequest request = new FAQRequest("제목", "본문");
        final FAQ faq = FAQ.create(request);
        faqRepository.save(faq);

        // when
        final ExtractableResponse<Response> response = changeVisibility(faq.getId(), adminAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("관리자가 아니라면 FAQ 보이기 여부 수정에 실패한다.")
    void invalid_role_change_visibility_faq() {
        // given
        final FAQRequest request = new FAQRequest("제목", "본문");
        final FAQ faq = FAQ.create(request);
        faqRepository.save(faq);

        // when
        final ExtractableResponse<Response> response = changeVisibility(faq.getId(), userAccessToken);
        final ErrorResponse errorResponse = response.body().as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(errorResponse.message()).isEqualTo("접근 권한이 없습니다.");
    }

    @Test
    @DisplayName("FAQ 순서를 변경한다.")
    void change_sequence_faq() {
        // given
        final FAQRequest request1 = new FAQRequest("제목1", "본문1");
        final FAQRequest request2 = new FAQRequest("제목2", "본문2");
        final FAQ faq1 = FAQ.create(request1);
        final FAQ faq2 = FAQ.create(request2);
        faqRepository.saveAll(List.of(faq1, faq2));

        final FAQChangeSequenceRequest request = new FAQChangeSequenceRequest(faq2.getId());

        // when
        final ExtractableResponse<Response> response = changeSequence(faq1.getId(), request, adminAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("관리자가 아니라면 FAQ 순서 변경에 실패한다.")
    void invalid_role_change_sequence_faq() {
        // given
        final FAQRequest request1 = new FAQRequest("제목1", "본문1");
        final FAQRequest request2 = new FAQRequest("제목2", "본문2");
        final FAQ faq1 = FAQ.create(request1);
        final FAQ faq2 = FAQ.create(request2);
        faqRepository.saveAll(List.of(faq1, faq2));

        final FAQChangeSequenceRequest request = new FAQChangeSequenceRequest(faq2.getId());

        // when
        final ExtractableResponse<Response> response = changeSequence(faq1.getId(), request, userAccessToken);
        final ErrorResponse errorResponse = response.body().as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(errorResponse.message()).isEqualTo("접근 권한이 없습니다.");
    }

    @Test
    @DisplayName("FAQ를 삭제한다.")
    void delete_faq() {
        final FAQRequest request = new FAQRequest("제목", "본문");
        final FAQ faq = FAQ.create(request);
        faqRepository.save(faq);

        // when
        final ExtractableResponse<Response> response = delete(faq.getId(), adminAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("관리자가 아니라면 FAQ 삭제에 실패한다.")
    void invalid_role_delete_faq() {
        final FAQRequest request = new FAQRequest("제목", "본문");
        final FAQ faq = FAQ.create(request);
        faqRepository.save(faq);

        // when
        final ExtractableResponse<Response> response = delete(faq.getId(), userAccessToken);
        final ErrorResponse errorResponse = response.body().as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(errorResponse.message()).isEqualTo("접근 권한이 없습니다.");
    }

    private ExtractableResponse<Response> findAllFAQs(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/faqs/list")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> findAllAdminFAQs(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/faqs/list/admin")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> create(final FAQRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/faqs")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> edit(final Long faqId, final FAQRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/api/faqs/" + faqId)
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> changeVisibility(final Long faqId, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/api/faqs/visibility/" + faqId)
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> changeSequence(final Long faqId,
                                                         final FAQChangeSequenceRequest request,
                                                         final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/api/faqs/sequence/" + faqId)
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> delete(final Long faqId, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/faqs/" + faqId)
                .then().log().all().extract();
    }
}
