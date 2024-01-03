package com.handwoong.rainbowletter.util;

import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static com.handwoong.rainbowletter.util.RestDocsUtils.setSpecification;
import static com.handwoong.rainbowletter.util.TestConstants.ADMIN_EMAIL;
import static com.handwoong.rainbowletter.util.TestConstants.ADMIN_PASSWORD;
import static com.handwoong.rainbowletter.util.TestConstants.USER_EMAIL;
import static com.handwoong.rainbowletter.util.TestConstants.USER_PASSWORD;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.member.controller.request.MemberLoginRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@Sql({"classpath:sql/member.sql"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
public class ControllerTestSupporter {
    protected static final MemberLoginRequest userRequest = new MemberLoginRequest(USER_EMAIL, USER_PASSWORD);
    protected static final MemberLoginRequest adminRequest = new MemberLoginRequest(ADMIN_EMAIL, ADMIN_PASSWORD);

    protected static String userAccessToken;
    protected static String adminAccessToken;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp(final RestDocumentationContextProvider provider) {
        RestAssured.port = port;
        final RequestSpecification specification = new RequestSpecBuilder()
                .addFilter(
                        documentationConfiguration(provider)
                                .operationPreprocessors()
                                .withRequestDefaults(RestDocsUtils.removeHeaders())
                                .withResponseDefaults(RestDocsUtils.removeHeaders())
                )
                .addFilter(RestDocsUtils.getFilter())
                .build();
        setSpecification(specification);
        userAccessToken = getToken(userRequest);
        adminAccessToken = getToken(adminRequest);
    }

    @AfterEach
    void clear() {
        databaseCleaner.execute();
    }

    private static String getToken(final MemberLoginRequest request) {
        final ExtractableResponse<Response> response = login(request);
        final TokenResponse result = response.body().as(TokenResponse.class);
        return result.token();
    }

    private static ExtractableResponse<Response> login(final MemberLoginRequest request) {
        return RestAssured
                .given(getSpecification()).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/members/login")
                .then().log().all().extract();
    }
}
