package com.handwoong.rainbowletter.image.controller.snippet;

import static com.handwoong.rainbowletter.util.SnippetAttributeGenerator.constraints;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;

import org.springframework.restdocs.snippet.Snippet;

public class ImageRequestSnippet {
    public static final Snippet IMAGE_HEADER = requestHeaders(
            headerWithName("Authorization").description("액세스 토큰"),
            headerWithName("Content-Type").description("폼 데이터")
    );
    public static final Snippet IMAGE_TYPE = queryParameters(
            parameterWithName("type").description("이미지 타입").attributes(constraints("PET || LETTER 대문자"))
    );
    public static final Snippet MULTIPART = requestParts(
            partWithName("file").description("업로드할 이미지").attributes(constraints("10MB 이하의 이미지 파일"))
    );

    private ImageRequestSnippet() {
    }
}
