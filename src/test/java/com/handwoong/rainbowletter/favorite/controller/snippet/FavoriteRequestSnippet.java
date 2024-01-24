package com.handwoong.rainbowletter.favorite.controller.snippet;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.snippet.Snippet;

public class FavoriteRequestSnippet {
    public static final Snippet PATH_PARAM_ID = pathParameters(
            parameterWithName("id").description("좋아요 ID")
    );

    private FavoriteRequestSnippet() {
    }
}
