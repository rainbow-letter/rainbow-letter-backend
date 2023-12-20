package com.handwoong.rainbowletter.controller.faq;

import static com.handwoong.rainbowletter.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.controller.ControllerTestProvider;
import com.handwoong.rainbowletter.domain.faq.FAQ;
import com.handwoong.rainbowletter.dto.faq.FAQResponse;
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
        final FAQ faq = FAQ.create("제목", "본문");
        faqRepository.save(faq);

        // when
        final ExtractableResponse<Response> response = findAllFAQs(userAccessToken);
        final FAQResponse faqResponse = response.body().as(FAQResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(faqResponse.faqs()).hasSize(1);
    }

    private ExtractableResponse<Response> findAllFAQs(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/faqs")
                .then().log().all().extract();
    }
}