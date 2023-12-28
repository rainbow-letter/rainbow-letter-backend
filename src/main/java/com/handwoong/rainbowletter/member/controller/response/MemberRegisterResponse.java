package com.handwoong.rainbowletter.member.controller.response;

import com.handwoong.rainbowletter.mail.domain.dto.EmailDto;
import com.handwoong.rainbowletter.member.domain.Member;

public record MemberRegisterResponse(
        String email
) implements EmailDto {
    public static MemberRegisterResponse from(final Member member) {
        return new MemberRegisterResponse(member.email().toString());
    }
}
