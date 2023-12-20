package com.handwoong.rainbowletter.dto.faq;

import static com.handwoong.rainbowletter.dto.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.dto.ValidateMessage.FAQ_SUMMARY;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FAQCreateRequest(
        @NotBlank(message = EMPTY_MESSAGE)
        @Size(max = 30, message = FAQ_SUMMARY)
        String summary,

        @NotBlank(message = EMPTY_MESSAGE)
        String detail
) {
}
