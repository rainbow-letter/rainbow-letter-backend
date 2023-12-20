package com.handwoong.rainbowletter.dto.member;

import com.handwoong.rainbowletter.domain.member.MemberRole;

public record MemberResponse(
        Long id,
        String email,
        String phoneNumber,
        MemberRole role
) {
}
