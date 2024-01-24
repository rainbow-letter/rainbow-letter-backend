package com.handwoong.rainbowletter.letter.controller.request;

import com.handwoong.rainbowletter.letter.domain.dto.ReplySubmit;

public record ReplySubmitRequest(
        Long letterId
) {
    public ReplySubmit toDto() {
        return ReplySubmit.builder()
                .letterId(letterId)
                .build();
    }
}
