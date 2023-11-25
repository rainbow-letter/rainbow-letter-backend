package com.handwoong.rainbowletter.dto.member;

import com.handwoong.rainbowletter.domain.member.Member;
import com.handwoong.rainbowletter.dto.mail.EmailDto;

public record MemberRegisterResponse(
        Long id,
        String email
) implements EmailDto {
    public static MemberRegisterResponse from(final Member member) {
        return new MemberRegisterResponse(member.getId(), member.getEmail());
    }
}
