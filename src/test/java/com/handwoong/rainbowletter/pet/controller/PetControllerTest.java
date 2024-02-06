package com.handwoong.rainbowletter.pet.controller;

import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.member.controller.snippet.MemberRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.rainbowletter.pet.controller.snippet.PetRequestSnippet.PATH_PARAM_ID;
import static com.handwoong.rainbowletter.pet.controller.snippet.PetRequestSnippet.PET_CREATE_REQUEST;
import static com.handwoong.rainbowletter.pet.controller.snippet.PetRequestSnippet.PET_UPDATE_REQUEST;
import static com.handwoong.rainbowletter.pet.controller.snippet.PetResponseSnippet.DASHBOARD_RESPONSE;
import static com.handwoong.rainbowletter.pet.controller.snippet.PetResponseSnippet.PET_RESPONSE;
import static com.handwoong.rainbowletter.pet.controller.snippet.PetResponseSnippet.PET_RESPONSES;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getFilter;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.handwoong.rainbowletter.pet.controller.request.PetCreateRequest;
import com.handwoong.rainbowletter.pet.controller.request.PetUpdateRequest;
import com.handwoong.rainbowletter.pet.controller.response.DashboardResponses;
import com.handwoong.rainbowletter.pet.controller.response.PetResponse;
import com.handwoong.rainbowletter.pet.controller.response.PetResponses;
import com.handwoong.rainbowletter.util.ControllerTestSupporter;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/member.sql", "classpath:sql/pet.sql"})
class PetControllerTest extends ControllerTestSupporter {
    @Test
    void 대시보드_조회() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = dashboard(token);
        final DashboardResponses result = response.body().as(DashboardResponses.class);

        // then
        assertThat(result.pets()).hasSize(2);
    }

    private ExtractableResponse<Response> dashboard(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(AUTHORIZATION_HEADER, DASHBOARD_RESPONSE))
                .when().get("/api/pets/dashboard")
                .then().log().all().extract();
    }

    @Test
    void 반려동물_목록_조회() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = findAll(token);
        final PetResponses result = response.body().as(PetResponses.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);

        assertThat(result.pets().get(0).id()).isEqualTo(1);
        assertThat(result.pets().get(0).name()).isEqualTo("콩이");
        assertThat(result.pets().get(0).species()).isEqualTo("고양이");
        assertThat(result.pets().get(0).owner()).isEqualTo("형님");
        assertThat(result.pets().get(0).personalities()).containsExactly("활발한", "잘삐짐");
        assertThat(result.pets().get(0).deathAnniversary()).isEqualTo(LocalDate.of(2023, 1, 1));
        assertThat(result.pets().get(0).image().id()).isEqualTo(1);
        assertThat(result.pets().get(0).image().url()).isEqualTo("http://rainbowletter/image");
        assertThat(result.pets().get(0).image().objectKey()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(result.pets().get(0).favorite().id()).isEqualTo(1);
        assertThat(result.pets().get(0).favorite().total()).isZero();
        assertThat(result.pets().get(0).favorite().dayIncreaseCount()).isZero();
        assertThat(result.pets().get(0).favorite().canIncrease()).isTrue();

        assertThat(result.pets().get(1).id()).isEqualTo(2);
        assertThat(result.pets().get(1).name()).isEqualTo("미키");
        assertThat(result.pets().get(1).species()).isEqualTo("강아지");
        assertThat(result.pets().get(1).owner()).isEqualTo("엄마");
        assertThat(result.pets().get(1).personalities()).isEmpty();
        assertThat(result.pets().get(1).deathAnniversary()).isNull();
        assertThat(result.pets().get(1).image().id()).isNull();
        assertThat(result.pets().get(1).favorite().id()).isEqualTo(2);
        assertThat(result.pets().get(1).favorite().total()).isZero();
        assertThat(result.pets().get(1).favorite().dayIncreaseCount()).isZero();
        assertThat(result.pets().get(1).favorite().canIncrease()).isTrue();
    }

    private ExtractableResponse<Response> findAll(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(AUTHORIZATION_HEADER, PET_RESPONSES))
                .when().get("/api/pets")
                .then().log().all().extract();
    }

    @Test
    void 반려동물_단건_조회() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = findByIdAndEmail(token);
        final PetResponse result = response.body().as(PetResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(result.id()).isEqualTo(1);
        assertThat(result.name()).isEqualTo("콩이");
        assertThat(result.species()).isEqualTo("고양이");
        assertThat(result.owner()).isEqualTo("형님");
        assertThat(result.personalities()).containsExactly("활발한", "잘삐짐");
        assertThat(result.deathAnniversary()).isEqualTo(LocalDate.of(2023, 1, 1));
        assertThat(result.image().id()).isEqualTo(1);
        assertThat(result.image().url()).isEqualTo("http://rainbowletter/image");
        assertThat(result.image().objectKey()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(result.favorite().id()).isEqualTo(1);
        assertThat(result.favorite().total()).isZero();
        assertThat(result.favorite().dayIncreaseCount()).isZero();
        assertThat(result.favorite().canIncrease()).isTrue();
    }

    private ExtractableResponse<Response> findByIdAndEmail(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(AUTHORIZATION_HEADER, PATH_PARAM_ID, PET_RESPONSE))
                .when().get("/api/pets/{id}", 1L)
                .then().log().all().extract();
    }

    @Test
    void 반려동물_생성() throws IOException {
        // given
        final String token = userAccessToken;
        final HashSet<String> personalities = new HashSet<>(List.of("용맹한", "잘삐짐"));
        final PetCreateRequest request =
                new PetCreateRequest("콩이", "고양이", "엄마", personalities, LocalDate.of(1900, 1, 1), null);

        // when
        final ExtractableResponse<Response> response = create(request, token);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    private ExtractableResponse<Response> create(final PetCreateRequest request,
                                                 final String token) throws IOException {
        final ObjectMapper mapper = getMapper();
        final String body = mapper.writeValueAsString(request);
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .filter(getFilter().document(AUTHORIZATION_HEADER, PET_CREATE_REQUEST))
                .when().post("/api/pets")
                .then().log().all().extract();
    }

    @Test
    void 반려동물_업데이트() throws IOException {
        // given
        final String token = userAccessToken;
        final HashSet<String> personalities = new HashSet<>(List.of("용맹한", "잘삐짐"));
        final PetUpdateRequest request =
                new PetUpdateRequest("루이", "호랑이", "형님", personalities, LocalDate.of(2000, 1, 1), 1L);

        // when
        final ExtractableResponse<Response> response = update(request, token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> update(final PetUpdateRequest request,
                                                 final String token) throws IOException {
        final ObjectMapper mapper = getMapper();
        final String body = mapper.writeValueAsString(request);
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .filter(getFilter().document(AUTHORIZATION_HEADER, PATH_PARAM_ID, PET_UPDATE_REQUEST))
                .when().put("/api/pets/{id}", 1)
                .then().log().all().extract();
    }

    @Test
    void 반려동물_이미지_삭제() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = deleteImage(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> deleteImage(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(getFilter().document(AUTHORIZATION_HEADER, PATH_PARAM_ID))
                .when().delete("/api/pets/{id}/image", 1)
                .then().log().all().extract();
    }

    @Test
    void 반려동물_삭제() {
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
                .filter(getFilter().document(AUTHORIZATION_HEADER, PATH_PARAM_ID))
                .when().delete("/api/pets/{id}", 1)
                .then().log().all().extract();
    }

    private ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }
}
