package com.handwoong.rainbowletter.member.domain.dto;

import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import lombok.Builder;

@Builder
public record PhoneNumberUpdate(PhoneNumber phoneNumber) {
}
