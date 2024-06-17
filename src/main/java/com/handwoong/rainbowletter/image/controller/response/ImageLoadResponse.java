package com.handwoong.rainbowletter.image.controller.response;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public record ImageLoadResponse(MediaType contentType, Resource content) {

    public static ImageLoadResponse of(final MediaType contentType, final Resource content) {
        return new ImageLoadResponse(contentType, content);
    }

}
