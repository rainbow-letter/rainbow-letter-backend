package com.handwoong.rainbowletter.letter.controller.response;

import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record LetterAdminResponse(
        Long id,
        Long memberId,
        String email,
        String summary,
        String content,
        String shareLink,
        Long count,
        LetterPetResponse pet,
        ImageResponse image,
        ReplyAdminResponse reply,
        LocalDateTime createdAt
) {
}
