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
import com.handwoong.rainbowletter.letter.controller.response.LetterBoxResponses;
import com.handwoong.rainbowletter.letter.controller.response.LetterResponse;
import com.handwoong.rainbowletter.letter.domain.LetterStatus;
import com.handwoong.rainbowletter.letter.domain.ReplyReadStatus;
import com.handwoong.rainbowletter.letter.domain.ReplyType;
import com.handwoong.rainbowletter.util.ControllerTestSupporter;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
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
        final LetterBoxResponses result = response.body().as(LetterBoxResponses.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);

        assertThat(result.letters().get(0).id()).isEqualTo(2);
        assertThat(result.letters().get(0).summary()).isEqualTo("콩아 형님이다.");
        assertThat(result.letters().get(0).status()).isEqualTo(LetterStatus.RESPONSE);
        assertThat(result.letters().get(0).readStatus()).isEqualTo(ReplyReadStatus.UNREAD);
        assertThat(result.letters().get(0).petName()).isEqualTo("콩이");
        assertThat(result.letters().get(0).createdAt()).isEqualTo(LocalDateTime.of(2023, 1, 2, 12, 0, 0));

        assertThat(result.letters().get(1).id()).isEqualTo(1);
        assertThat(result.letters().get(1).summary()).isEqualTo("미키야 엄마가 보고싶다.");
        assertThat(result.letters().get(1).status()).isEqualTo(LetterStatus.RESPONSE);
        assertThat(result.letters().get(1).readStatus()).isEqualTo(ReplyReadStatus.UNREAD);
        assertThat(result.letters().get(1).petName()).isEqualTo("미키");
        assertThat(result.letters().get(1).createdAt()).isEqualTo(LocalDateTime.of(2023, 1, 1, 12, 0, 0));
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
        final LetterResponse result = response.body().as(LetterResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(result.id()).isEqualTo(1);
        assertThat(result.summary()).isEqualTo("미키야 엄마가 보고싶다.");
        assertThat(result.content()).isEqualTo("미키야 엄마가 보고싶다. 엄마는 오늘 미키 생각하면서 그림을 그렸어.");
        assertThat(result.pet().id()).isEqualTo(2);
        assertThat(result.pet().name()).isEqualTo("미키");
        assertThat(result.pet().image().id()).isNull();
        assertThat(result.pet().image().objectKey()).isNull();
        assertThat(result.pet().image().url()).isNull();
        assertThat(result.image().id()).isEqualTo(2);
        assertThat(result.image().objectKey()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(result.image().url()).isEqualTo("http://rainbowletter/image");
        assertThat(result.reply().id()).isEqualTo(1);
        assertThat(result.reply().summary()).isEqualTo("엄마 미키 여기서 잘 지내!");
        assertThat(result.reply().content()).isEqualTo("엄마 미키 여기서 잘 지내! 여기 무지개마을은 매일 햇살이 따뜻해. 미키 언제나 엄마 곁에 있을게. 사랑해!");
        assertThat(result.reply().readStatus()).isEqualTo(ReplyReadStatus.UNREAD);
        assertThat(result.reply().type()).isEqualTo(ReplyType.REPLY);
        assertThat(result.createdAt()).isEqualTo(LocalDateTime.of(2023, 1, 1, 12, 0, 0));
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
