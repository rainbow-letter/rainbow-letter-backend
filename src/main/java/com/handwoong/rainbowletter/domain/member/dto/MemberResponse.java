package com.handwoong.rainbowletter.domain.member.dto;

import com.handwoong.rainbowletter.domain.member.model.MemberRole;

public record MemberResponse(
        Long id,
        String email,
        String phoneNumber,
        MemberRole role
) {
}
