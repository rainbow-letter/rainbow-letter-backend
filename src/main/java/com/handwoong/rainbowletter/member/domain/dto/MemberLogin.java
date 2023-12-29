package com.handwoong.rainbowletter.member.domain.dto;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Password;
import lombok.Builder;

@Builder
public record MemberLogin(Email email, Password password) {
}
