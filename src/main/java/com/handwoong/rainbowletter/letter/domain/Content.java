package com.handwoong.rainbowletter.letter.domain;

import com.handwoong.rainbowletter.letter.exception.ContentFormatNotValidException;
import org.springframework.util.StringUtils;

public record Content(String content) {
    public Content {
        validateNull(content);
    }

    private void validateNull(final String content) {
        if (!StringUtils.hasText(content)) {
            throw new ContentFormatNotValidException(content);
        }
    }

    @Override
    public String toString() {
        return content;
    }
}
