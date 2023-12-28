package com.handwoong.rainbowletter.member.controller.response;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberRole;
import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import lombok.Builder;

@Builder
public record MemberInfoResponse(
        Long id,
        Email email,
        PhoneNumber phoneNumber,
        MemberRole role
) {
    public static MemberInfoResponse from(final Member member) {
        return MemberInfoResponse.builder()
                .id(member.id())
                .email(member.email())
                .phoneNumber(member.phoneNumber())
                .role(member.role())
                .build();
    }
}
