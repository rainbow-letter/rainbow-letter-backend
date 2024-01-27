package com.handwoong.rainbowletter.letter.domain;

import com.handwoong.rainbowletter.letter.exception.ContentFormatNotValidException;
import org.springframework.util.StringUtils;

public record Content(String content) {
    public static final int MAX_CONTENT_LENGTH = 2000;

    public Content {
        validateNull(content);
        validateFormat(content);
    }

    private void validateNull(final String content) {
        if (!StringUtils.hasText(content)) {
            throw new ContentFormatNotValidException(content);
        }
    }

    private void validateFormat(final String content) {
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new ContentFormatNotValidException(content);
        }
    }

    @Override
    public String toString() {
        return content;
    }
}
