package com.handwoong.rainbowletter.member.controller;

import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.FIND_PASSWORD_REQUEST;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.LOGIN_REQUEST;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberResponseSnippet.INFO_RESPONSE;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberResponseSnippet.LOGIN_RESPONSE;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getFilter;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.common.util.jwt.GrantType;
import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.member.controller.request.FindPasswordRequest;
import com.handwoong.rainbowletter.member.controller.request.MemberLoginRequest;
import com.handwoong.rainbowletter.member.controller.response.MemberInfoResponse;
import com.handwoong.rainbowletter.member.domain.MemberRole;
import com.handwoong.rainbowletter.util.ControllerTestSupporter;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class MemberReadControllerTest extends ControllerTestSupporter {
    @Test
    void 회원_정보_조회() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = info(token);
        final MemberInfoResponse result = response.body().as(MemberInfoResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(result.id()).isEqualTo(2);
        assertThat(result.email()).isEqualTo("user@mail.com");
        assertThat(result.phoneNumber()).isEqualTo("01033334444");
        assertThat(result.role()).isEqualTo(MemberRole.ROLE_USER);
    }

    private ExtractableResponse<Response> info(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(AUTHORIZATION_HEADER, INFO_RESPONSE))
                .when().get("/api/members/info")
                .then().log().all().extract();
    }

    @Test
    void 회원_로그인() {
        // given
        final MemberLoginRequest request = new MemberLoginRequest("user@mail.com", "user1234");

        // when
        final ExtractableResponse<Response> response = login(request);
        final TokenResponse result = response.body().as(TokenResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(result.grantType()).isEqualTo(GrantType.BEARER.getName());
        assertThat(result.token()).isNotBlank();
    }

    private ExtractableResponse<Response> login(final MemberLoginRequest request) {
        return RestAssured
                .given(getSpecification()).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .filter(getFilter().document(LOGIN_REQUEST, LOGIN_RESPONSE))
                .when().post("/api/members/login")
                .then().log().all().extract();
    }

    @Test
    void 회원_토큰_검증() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = verify(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> verify(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(AUTHORIZATION_HEADER))
                .when().post("/api/members/verify")
                .then().log().all().extract();
    }

    @Test
    void 회원_비밀번호_찾기() {
        // given
        final FindPasswordRequest request = new FindPasswordRequest("user@mail.com");

        // when
        final ExtractableResponse<Response> response = findPassword(request);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> findPassword(final FindPasswordRequest request) {
        return RestAssured
                .given(getSpecification()).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .filter(getFilter().document(FIND_PASSWORD_REQUEST))
                .when().post("/api/members/password/find")
                .then().log().all().extract();
    }
}