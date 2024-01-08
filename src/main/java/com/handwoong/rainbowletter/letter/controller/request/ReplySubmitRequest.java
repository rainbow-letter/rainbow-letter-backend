package com.handwoong.rainbowletter.letter.controller.request;

import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.EMPTY_MESSAGE;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.LETTER_CONTENT;
import static com.handwoong.rainbowletter.common.util.validation.ValidateMessage.LETTER_SUMMARY;
import static com.handwoong.rainbowletter.letter.domain.Content.MAX_CONTENT_LENGTH;
import static com.handwoong.rainbowletter.letter.domain.Summary.MAX_SUMMARY_LENGTH;

import com.handwoong.rainbowletter.letter.domain.Content;
import com.handwoong.rainbowletter.letter.domain.Summary;
import com.handwoong.rainbowletter.letter.domain.dto.ReplySubmit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReplySubmitRequest(
        Long letterId,

        @NotBlank(message = EMPTY_MESSAGE)
        @Size(max = MAX_SUMMARY_LENGTH, message = LETTER_SUMMARY)
        String summary,

        @NotBlank(message = EMPTY_MESSAGE)
        @Size(max = MAX_CONTENT_LENGTH, message = LETTER_CONTENT)
        String content
) {
    public ReplySubmit toDto() {
        return ReplySubmit.builder()
                .letterId(letterId)
                .summary(new Summary(summary))
                .content(new Content(content))
                .build();
    }
}
