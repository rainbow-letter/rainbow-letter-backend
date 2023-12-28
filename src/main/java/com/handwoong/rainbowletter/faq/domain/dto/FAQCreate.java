package com.handwoong.rainbowletter.faq.domain.dto;

import static com.handwoong.rainbowletter.common.util.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.ValidateMessage.FAQ_SUMMARY;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FAQCreate(
        @NotBlank(message = EMPTY_MESSAGE)
        @Size(max = 30, message = FAQ_SUMMARY)
        String summary,

        @NotBlank(message = EMPTY_MESSAGE)
        String detail
) {
}
