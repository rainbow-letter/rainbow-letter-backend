package com.handwoong.rainbowletter.domain.member.dto;

import com.handwoong.rainbowletter.domain.member.model.Member;
import com.handwoong.rainbowletter.dto.mail.EmailDto;

public record MemberRegisterResponse(
        Long id,
        String email
) implements EmailDto {
    public static MemberRegisterResponse from(final Member member) {
        return new MemberRegisterResponse(member.getId(), member.getEmail());
    }
}
