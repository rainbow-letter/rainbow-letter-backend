package com.handwoong.rainbowletter.controller.member;

import static com.handwoong.rainbowletter.util.Constants.NEW_EMAIL;
import static com.handwoong.rainbowletter.util.Constants.NEW_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.handwoong.rainbowletter.config.security.GrantType;
import com.handwoong.rainbowletter.config.security.TokenResponse;
import com.handwoong.rainbowletter.controller.ControllerTestProvider;
import com.handwoong.rainbowletter.dto.member.MemberLoginRequest;
import com.handwoong.rainbowletter.dto.member.MemberRegisterRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MemberControllerTest extends ControllerTestProvider {
    public static String getToken(final MemberLoginRequest loginRequest) {
        final ExtractableResponse<Response> loginResponse = login(loginRequest);
        final TokenResponse tokenResponse = loginResponse.body().as(TokenResponse.class);
        return tokenResponse.token();
    }

    private static ExtractableResponse<Response> register(final MemberRegisterRequest registerRequest) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(registerRequest)
                .when().post("/api/members")
                .then().log().all().extract();
    }

    private static ExtractableResponse<Response> login(final MemberLoginRequest loginRequest) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
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
    @DisplayName("유효한 로그인 요청이 들어오면 토큰을 발급한다.")
    void login() {
        // given
        final MemberRegisterRequest registerRequest = new MemberRegisterRequest(NEW_EMAIL, NEW_PASSWORD);
        register(registerRequest);

        final MemberLoginRequest loginRequest = new MemberLoginRequest(NEW_EMAIL, NEW_PASSWORD);

        // when
        final ExtractableResponse<Response> loginResponse = login(loginRequest);
        final TokenResponse tokenResponse = loginResponse.body().as(TokenResponse.class);

        // then
        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(tokenResponse.grantType()).isEqualTo(GrantType.BEARER.getName());
        assertThat(tokenResponse.token()).isInstanceOf(String.class);
    }
}
