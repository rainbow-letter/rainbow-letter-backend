package com.handwoong.rainbowletter.member.controller.response;

import com.handwoong.rainbowletter.mail.dto.EmailDto;
import com.handwoong.rainbowletter.member.infrastructure.Member;

public record MemberRegisterResponse(
        Long id,
        String email
) implements EmailDto {
    public static MemberRegisterResponse from(final Member member) {
        return new MemberRegisterResponse(member.getId(), member.getEmail());
    }
}
