package com.handwoong.rainbowletter.domain.member.dto;

import com.handwoong.rainbowletter.domain.mail.dto.EmailDto;
import com.handwoong.rainbowletter.domain.member.model.Member;

public record MemberRegisterResponse(
        Long id,
        String email
) implements EmailDto {
    public static MemberRegisterResponse from(final Member member) {
        return new MemberRegisterResponse(member.getId(), member.getEmail());
    }
}
