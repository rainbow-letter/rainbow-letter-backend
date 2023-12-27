package com.handwoong.rainbowletter.member.controller.response;

import com.handwoong.rainbowletter.member.domain.MemberRole;

public record MemberResponse(
        Long id,
        String email,
        String phoneNumber,
        MemberRole role
) {
}
