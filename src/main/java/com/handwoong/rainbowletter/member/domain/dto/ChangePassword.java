package com.handwoong.rainbowletter.member.domain.dto;

import com.handwoong.rainbowletter.member.domain.Password;

public record ChangePassword(Password password, Password newPassword) {
}
