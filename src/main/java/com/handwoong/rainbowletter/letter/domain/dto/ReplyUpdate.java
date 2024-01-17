package com.handwoong.rainbowletter.letter.domain.dto;

import com.handwoong.rainbowletter.letter.domain.Content;
import com.handwoong.rainbowletter.letter.domain.Summary;
import lombok.Builder;

@Builder
public record ReplyUpdate(Summary summary, Content content) {
}
