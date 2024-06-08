package com.handwoong.rainbowletter.letter.controller.response;

import java.time.LocalDateTime;

import com.handwoong.rainbowletter.image.controller.response.ImageResponse;
import com.handwoong.rainbowletter.member.domain.OAuthProvider;

import lombok.Builder;

@Builder
public record LetterAdminResponse(
        Long id,
        Long memberId,
        String email,
        String phoneNumber,
        OAuthProvider provider,
        LocalDateTime memberCreatedAt,
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
