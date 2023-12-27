package com.handwoong.rainbowletter.domain.pet.controller;

import static com.handwoong.rainbowletter.common.config.security.jwt.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.common.config.security.jwt.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.rainbowletter.domain.ControllerTestProvider;
import com.handwoong.rainbowletter.pet.domain.dto.PetRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;


@Sql({"classpath:sql/member.sql", "classpath:sql/pet.sql"})
class PetResponseControllerTest extends ControllerTestProvider {
    @Test
    @DisplayName("반려 동물 목록을 조회한다.")
    void info_pets() {
        // given
        // when
        final ExtractableResponse<Response> response = infoPets(userAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("반려 동물을 조회한다.")
    void info_pet() {
        // given
        // when
        final ExtractableResponse<Response> response = infoPet(userAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("유효한 요청이 들어오면 반려 동물을 생성한다.")
    void create_pet() {
        // given
        final PetRequest request =
                new PetRequest("두부", "고양이", "형님", new HashSet<>(List.of("활발한")), LocalDate.of(2020, 1, 1), null);

        // when
        final ExtractableResponse<Response> response = create(request, userAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("유효하지 않은 요청이 들어오면 반려 동물 생성에 실패한다.")
    void invalid_create_pet() {
        // given
        final PetRequest request = new PetRequest(
                "두부", "고양이고양이고양이고양", "형님", new HashSet<>(List.of("활발한")), LocalDate.of(2020, 1, 1), null);

        // when
        final ExtractableResponse<Response> response = create(request, userAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("유효한 요청이 들어오면 반려 동물을 수정한다.")
    void edit_pet() {
        // given
        final PetRequest request =
                new PetRequest("장두부", "호랑이", "동생", new HashSet<>(List.of("활발한")), LocalDate.of(2020, 1, 1), null);

        // when
        final ExtractableResponse<Response> response = edit(request, userAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("유효하지 않은 요청이 들어오면 반려 동물 수정에 실패한다.")
    void invalid_edit_pet() {
        // given
        final PetRequest request = new PetRequest(
                "두부", "고양이고양이고양이고양", "형님", new HashSet<>(List.of("활발한")), LocalDate.of(2020, 1, 1), null);

        // when
        final ExtractableResponse<Response> response = edit(request, userAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("반려 동물을 삭제한다.")
    void delete_pet() {
        // given
        // when
        final ExtractableResponse<Response> response = delete(userAccessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> infoPets(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .when().get("/api/pets")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> infoPet(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .when().get("/api/pets/1")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> create(final PetRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .body(request)
                .when().post("/api/pets")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> edit(final PetRequest request, final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .body(request)
                .when().put("/api/pets/1")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> delete(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .when().delete("/api/pets/1")
                .then().log().all().extract();
    }
}