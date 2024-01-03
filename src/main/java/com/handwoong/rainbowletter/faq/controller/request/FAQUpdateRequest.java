package com.handwoong.rainbowletter.faq.controller.request;

import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.FAQ_SUMMARY;

import com.handwoong.rainbowletter.faq.domain.dto.FAQUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FAQUpdateRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Size(max = 30, message = FAQ_SUMMARY)
        String summary,

        @NotBlank(message = EMPTY_MESSAGE)
        String detail
) {
    public FAQUpdate toDto() {
        return FAQUpdate.builder()
                .summary(summary)
                .detail(detail)
                .build();
    }
}
