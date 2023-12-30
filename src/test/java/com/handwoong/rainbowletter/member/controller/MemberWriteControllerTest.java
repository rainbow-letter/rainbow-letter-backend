package com.handwoong.rainbowletter.member.controller;

import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.CHANGE_PASSWORD_REQUEST;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.CHANGE_PHONE_NUMBER_REQUEST;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.REGISTER_REQUEST;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.RESET_PASSWORD_REQUEST;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberResponseSnippet.REGISTER_RESPONSE;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getFilter;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static com.handwoong.rainbowletter.util.TestConstants.NEW_EMAIL;
import static com.handwoong.rainbowletter.util.TestConstants.NEW_PASSWORD;
import static com.handwoong.rainbowletter.util.TestConstants.USER_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.member.controller.request.ChangePasswordRequest;
import com.handwoong.rainbowletter.member.controller.request.MemberRegisterRequest;
import com.handwoong.rainbowletter.member.controller.request.PhoneNumberUpdateRequest;
import com.handwoong.rainbowletter.member.controller.request.ResetPasswordRequest;
import com.handwoong.rainbowletter.member.controller.response.MemberRegisterResponse;
import com.handwoong.rainbowletter.util.ControllerTestSupporter;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class MemberWriteControllerTest extends ControllerTestSupporter {
    @Test
    void 회원_가입() {
        // given
        final MemberRegisterRequest request = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);

        // when
        final ExtractableResponse<Response> response = register(request);
        final MemberRegisterResponse result = response.body().as(MemberRegisterResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(result.email()).isEqualTo("handwoong@gmail.com");
    }

    private ExtractableResponse<Response> register(final MemberRegisterRequest request) {
        return RestAssured
                .given(getSpecification()).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .filter(getFilter().document(REGISTER_REQUEST, REGISTER_RESPONSE))
                .when().post("/api/members")
                .then().log().all().extract();
    }

    @Test
    void 회원_비밀번호_변경() {
        // given
        final ChangePasswordRequest request = new ChangePasswordRequest(USER_PASSWORD, "!@#$password123");
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = changePassword(request, token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> changePassword(final ChangePasswordRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .filter(getFilter().document(AUTHORIZATION_HEADER, CHANGE_PASSWORD_REQUEST))
                .when().put("/api/members/password")
                .then().log().all().extract();
    }

    @Test
    void 회원_비밀번호_초기화() {
        // given
        final ResetPasswordRequest request = new ResetPasswordRequest("!@#$password1234");
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = resetPassword(request, token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> resetPassword(final ResetPasswordRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .filter(getFilter().document(AUTHORIZATION_HEADER, RESET_PASSWORD_REQUEST))
                .when().put("/api/members/password/reset")
                .then().log().all().extract();
    }

    @Test
    void 회원_휴대폰_번호_변경() {
        // given
        final PhoneNumberUpdateRequest request = new PhoneNumberUpdateRequest("01011112222");
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = changePhoneNumber(request, token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> changePhoneNumber(final PhoneNumberUpdateRequest request,
                                                            final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .filter(getFilter().document(AUTHORIZATION_HEADER, CHANGE_PHONE_NUMBER_REQUEST))
                .when().put("/api/members/phoneNumber")
                .then().log().all().extract();
    }

    @Test
    void 회원_휴대폰_번호_삭제() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = deletePhoneNumber(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> deletePhoneNumber(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(AUTHORIZATION_HEADER))
                .when().delete("/api/members/phoneNumber")
                .then().log().all().extract();
    }

    @Test
    void 회원_탈퇴() {
        // given
        final String token = userAccessToken;

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
                .filter(getFilter().document(AUTHORIZATION_HEADER))
                .when().delete("/api/members/leave")
                .then().log().all().extract();
    }
}
