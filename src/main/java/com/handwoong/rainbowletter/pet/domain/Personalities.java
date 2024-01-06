package com.handwoong.rainbowletter.pet.domain;

import com.handwoong.rainbowletter.pet.exception.PersonalityFormatNotValidException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import org.springframework.util.StringUtils;

@Builder
public record Personalities(Set<String> personalities) {
    public static final int MAX_PERSONALITY_SIZE = 3;
    public static final int MAX_PERSONALITY_LENGTH = 10;

    private static final String DELIMITER = ",";

    public Personalities {
        validateNull(personalities);
        validateSize(personalities);
        validateLength(personalities);
    }

    private void validateNull(final Set<String> personalities) {
        for (String personality : personalities) {
            if (!StringUtils.hasText(personality)) {
                throw new PersonalityFormatNotValidException(personality);
            }
        }
    }

    private void validateSize(final Set<String> personalities) {
        if (personalities.size() > MAX_PERSONALITY_SIZE) {
            throw new PersonalityFormatNotValidException(personalities.toString());
        }
    }

    private void validateLength(final Set<String> personalities) {
        for (String personality : personalities) {
            if (personality.length() > MAX_PERSONALITY_LENGTH) {
                throw new PersonalityFormatNotValidException(personality);
            }
        }
    }

    public static Personalities from(final String personalities) {
        final Set<String> target = Arrays.stream(personalities.split(DELIMITER))
                .filter(StringUtils::hasText)
                .collect(Collectors.toSet());
        return from(target);
    }

    public static Personalities from(final Set<String> personalities) {
        return Personalities.builder()
                .personalities(personalities)
                .build();
    }

    @Override
    public String toString() {
        return String.join(DELIMITER, personalities);
    }
}
