package com.handwoong.rainbowletter.dto.member;

import com.handwoong.rainbowletter.domain.member.Member;

public record MemberRegisterResponse(
        Long id
) {
    public static MemberRegisterResponse from(final Member member) {
        return new MemberRegisterResponse(member.getId());
    }
}
