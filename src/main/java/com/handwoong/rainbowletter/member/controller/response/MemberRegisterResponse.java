package com.handwoong.rainbowletter.member.controller.response;

import com.handwoong.rainbowletter.member.domain.Member;
import lombok.Builder;

@Builder
public record MemberRegisterResponse(String email) {
    public static MemberRegisterResponse from(final Member member) {
        return MemberRegisterResponse.builder()
                .email(member.email().toString())
                .build();
    }
}
