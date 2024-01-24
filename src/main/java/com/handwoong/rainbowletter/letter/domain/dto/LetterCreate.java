package com.handwoong.rainbowletter.letter.domain.dto;

import com.handwoong.rainbowletter.letter.domain.Content;
import com.handwoong.rainbowletter.letter.domain.Summary;
import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record LetterCreate(Summary summary, Content content, @Nullable Long image) {
}
