package com.handwoong.rainbowletter.member.controller.response;

import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberRole;
import java.util.Objects;
import lombok.Builder;

@Builder
public record MemberInfoResponse(Long id, String email, String phoneNumber, MemberRole role) {
    public static MemberInfoResponse from(final Member member) {
        return MemberInfoResponse.builder()
                .id(member.id())
                .email(member.email().toString())
                .phoneNumber(Objects.nonNull(member.phoneNumber()) ? member.phoneNumber().toString() : null)
                .role(member.role())
                .build();
    }
}
