package com.handwoong.rainbowletter.letter.domain.dto;

import com.handwoong.rainbowletter.letter.domain.Content;
import com.handwoong.rainbowletter.letter.domain.Summary;
import lombok.Builder;

@Builder
public record ReplySubmit(Long letterId, Summary summary, Content content) {
}
