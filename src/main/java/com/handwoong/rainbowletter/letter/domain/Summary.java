package com.handwoong.rainbowletter.letter.domain;

import com.handwoong.rainbowletter.letter.exception.SummaryFormatNotValidException;
import org.springframework.util.StringUtils;

public record Summary(String summary) {
    public static final int MAX_SUMMARY_LENGTH = 20;

    public Summary {
        validateNull(summary);
        validateFormat(summary);
    }

    private void validateNull(final String summary) {
        if (!StringUtils.hasText(summary)) {
            throw new SummaryFormatNotValidException(summary);
        }
    }

    private void validateFormat(final String summary) {
        if (summary.length() > MAX_SUMMARY_LENGTH) {
            throw new SummaryFormatNotValidException(summary);
        }
    }

    @Override
    public String toString() {
        return summary;
    }
}
