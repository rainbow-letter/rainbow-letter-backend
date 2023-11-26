package com.handwoong.rainbowletter.controller.member;

import static com.handwoong.rainbowletter.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.util.Constants.NEW_EMAIL;
import static com.handwoong.rainbowletter.util.Constants.NEW_PASSWORD;
import static com.handwoong.rainbowletter.util.Constants.USER_EMAIL;
import static com.handwoong.rainbowletter.util.Constants.USER_PASSWORD;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.handwoong.rainbowletter.config.security.GrantType;
import com.handwoong.rainbowletter.config.security.JwtTokenProvider;
import com.handwoong.rainbowletter.config.security.TokenResponse;
import com.handwoong.rainbowletter.controller.ControllerTestProvider;
import com.handwoong.rainbowletter.dto.member.MemberLoginRequest;
import com.handwoong.rainbowletter.dto.member.MemberRegisterRequest;
import com.handwoong.rainbowletter.service.mail.template.EmailTemplateType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MemberControllerTest extends ControllerTestProvider {
    @Autowired
    private JwtTokenProvider tokenProvider;

    public static String getToken(final MemberLoginRequest loginRequest) {
        final ExtractableResponse<Response> loginResponse = login(loginRequest);
        final TokenResponse tokenResponse = loginResponse.body().as(TokenResponse.class);
        return tokenResponse.token();
    }

    private static ExtractableResponse<Response> register(final MemberRegisterRequest registerRequest) {
        return RestAssured
                .given(getSpecification()).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(registerRequest)
                .when().post("/api/members")
                .then().log().all().extract();
    }

    private static ExtractableResponse<Response> login(final MemberLoginRequest loginRequest) {
        return RestAssured
                .given(getSpecification()).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/api/members/login")
                .then().log().all().extract();
    }

    @Test
    @DisplayName("유효한 회원가입 요청이 들어오면 회원가입에 성공한다.")
    void register_member() {
        // given
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);

        // when
        final ExtractableResponse<Response> registerResponse = register(registerRequest);

        // then
        assertThat(registerResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("중복된 이메일이 존재하면 400 예외가 발생한다.")
    void duplicate_register_member() {
        // given
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(USER_EMAIL, USER_PASSWORD);

        // when
        final ExtractableResponse<Response> registerResponse = register(registerRequest);

        // then
        assertThat(registerResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("유효한 이메일 인증 요청이 들어오면 인증에 성공한다.")
    void verify_member_email() {
        // given
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);
        register(registerRequest);

        final TokenResponse tokenResponse =
                tokenProvider.generateToken(GrantType.PATH_VARIABLE, NEW_EMAIL, EmailTemplateType.VERIFY.name());

        // when
        final ExtractableResponse<Response> verifyResponse = verify(tokenResponse.token());

        // then
        assertThat(verifyResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("이메일 인증 시 유효하지 않은 토큰일 경우 401 예외가 발생한다.")
    void verify_member_email_invalid_token() {
        // given
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);
        register(registerRequest);

        final TokenResponse tokenResponse =
                tokenProvider.generateToken(GrantType.PATH_VARIABLE, NEW_EMAIL, EmailTemplateType.VERIFY.name());

        // when
        final ExtractableResponse<Response> verifyResponse = verify(tokenResponse.token() + "fail");

        // then
        assertThat(verifyResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(verifyResponse.body().jsonPath().getString("message")).isEqualTo("유효하지 않은 토큰입니다.");
    }

    @Test
    @DisplayName("로그인에 성공한 사용자가 회원가입 시도 시 403 예외가 발생한다.")
    void already_login_member_register() {
        // given
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);

        // when
        final ExtractableResponse<Response> registerResponse = register(registerRequest, userAccessToken);

        // then
        assertThat(registerResponse.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("유효한 로그인 요청이 들어오면 토큰을 발급한다.")
    void login() {
        // given
        final MemberLoginRequest loginRequest = new MemberLoginRequest(USER_EMAIL, USER_PASSWORD);

        // when
        final ExtractableResponse<Response> loginResponse = login(loginRequest);
        final TokenResponse tokenResponse = loginResponse.body().as(TokenResponse.class);

        // then
        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(tokenResponse.grantType()).isEqualTo(GrantType.BEARER.getName());
        assertThat(tokenResponse.token()).isInstanceOf(String.class);
    }

    @Test
    @DisplayName("이메일 인증을 완료하지 않은 사용자가 로그인 시도 시 401 예외가 발생한다.")
    void not_verify_email_member_login() {
        // given
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);
        register(registerRequest);

        final MemberLoginRequest loginRequest = new MemberLoginRequest(NEW_EMAIL, NEW_PASSWORD);

        // when
        final ExtractableResponse<Response> loginResponse = login(loginRequest);

        // then
        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("존재하지 않는 사용자 이메일로 로그인 시도 시 400 예외가 발생한다.")
    void login_notfound_member() {
        // given
        final MemberLoginRequest loginRequest = new MemberLoginRequest(NEW_EMAIL, NEW_PASSWORD);

        // when
        final ExtractableResponse<Response> loginResponse = login(loginRequest);

        // then
        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("로그인에 성공한 사용자가 로그인 시도 시 403 예외가 발생한다.")
    void already_login_member_login() {
        // given
        final MemberLoginRequest loginRequest = new MemberLoginRequest(USER_EMAIL, USER_PASSWORD);

        // when
        final ExtractableResponse<Response> loginResponse = login(loginRequest, userAccessToken);

        // then
        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    private ExtractableResponse<Response> register(final MemberRegisterRequest registerRequest, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(registerRequest)
                .when().post("/api/members")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> verify(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/members/verify/" + token)
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> login(final MemberLoginRequest loginRequest, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/api/members/login")
                .then().log().all().extract();
    }
}
