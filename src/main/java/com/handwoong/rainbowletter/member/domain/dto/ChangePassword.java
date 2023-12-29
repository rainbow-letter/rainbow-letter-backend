package com.handwoong.rainbowletter.member.domain.dto;

import com.handwoong.rainbowletter.member.domain.Password;
import lombok.Builder;

@Builder
public record ChangePassword(Password password, Password newPassword) {
}
