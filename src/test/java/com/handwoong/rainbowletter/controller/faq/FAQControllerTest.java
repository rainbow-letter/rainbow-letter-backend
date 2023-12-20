package com.handwoong.rainbowletter.controller.faq;

import static com.handwoong.rainbowletter.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.controller.ControllerTestProvider;
import com.handwoong.rainbowletter.domain.faq.FAQ;
import com.handwoong.rainbowletter.dto.faq.FAQCreateRequest;
import com.handwoong.rainbowletter.dto.faq.FAQResponse;
import com.handwoong.rainbowletter.exception.ErrorResponse;
import com.handwoong.rainbowletter.repository.faq.FAQRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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
        final FAQCreateRequest request = new FAQCreateRequest("제목", "본문");
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
    @DisplayName("FAQ를 생성한다.")
    void create_faq() {
        // given
        final FAQCreateRequest request = new FAQCreateRequest("제목", "본문");

        // when
        final ExtractableResponse<Response> response = create(request, adminAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("관리자가 아니라면 FAQ 생성에 실패한다.")
    void invalid_role_create_faq() {
        // given
        final FAQCreateRequest request = new FAQCreateRequest("제목", "본문");

        // when
        final ExtractableResponse<Response> response = create(request, userAccessToken);
        final ErrorResponse errorResponse = response.body().as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(errorResponse.message()).isEqualTo("접근 권한이 없습니다.");
    }

    @Test
    @DisplayName("FAQ를 삭제한다.")
    void delete_faq() {
        final FAQCreateRequest request = new FAQCreateRequest("제목", "본문");
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
        final FAQCreateRequest request = new FAQCreateRequest("제목", "본문");
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

    private ExtractableResponse<Response> create(final FAQCreateRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/faqs")
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
