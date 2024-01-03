package com.handwoong.rainbowletter.image.controller;

import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.rainbowletter.common.config.security.JwtTokenAuthenticationFilter.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.rainbowletter.image.controller.snippet.ImageRequestSnippet.IMAGE_HEADER;
import static com.handwoong.rainbowletter.image.controller.snippet.ImageRequestSnippet.IMAGE_TYPE;
import static com.handwoong.rainbowletter.image.controller.snippet.ImageRequestSnippet.MULTIPART;
import static com.handwoong.rainbowletter.image.controller.snippet.ImageResponseSnippet.IMAGE_ID_RESPONSE;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getFilter;
import static com.handwoong.rainbowletter.util.RestDocsUtils.getSpecification;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import com.handwoong.rainbowletter.image.controller.response.ImageUploadResponse;
import com.handwoong.rainbowletter.image.domain.ImageType;
import com.handwoong.rainbowletter.image.service.port.AmazonS3Service;
import com.handwoong.rainbowletter.util.ControllerTestSupporter;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.File;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

@Sql({"classpath:sql/member.sql", "classpath:sql/image.sql"})
class ImageControllerTest extends ControllerTestSupporter {
    @MockBean
    private AmazonS3Service amazonS3Service;

    @Test
    void 이미지_업로드() {
        // given
        final String token = userAccessToken;
        BDDMockito.given(amazonS3Service.upload(any(MultipartFile.class), any(String.class)))
                .willReturn("http://rainbowletter/image");
        BDDMockito.given(amazonS3Service.getBucketName())
                .willReturn("rainbowletter");

        // when
        final ExtractableResponse<Response> response = upload(token);
        final ImageUploadResponse result = response.body().as(ImageUploadResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(result.id()).isEqualTo(2);
    }

    private ExtractableResponse<Response> upload(final String token) {
        return RestAssured
                .given(getSpecification()).log().all()
                .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("file", new File("src/test/resources/image/rainbow-letter-logo.png"))
                .queryParam("type", ImageType.PET)
                .filter(getFilter().document(IMAGE_HEADER, MULTIPART, IMAGE_TYPE, IMAGE_ID_RESPONSE))
                .when().post("/api/images/upload")
                .then().log().all().extract();
    }
}
