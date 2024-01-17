package com.handwoong.rainbowletter.sms.domain;

import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import lombok.Builder;

@Builder
public record Sms(Long id, int code, String statusMessage, PhoneNumber sender, PhoneNumber receiver, String content) {
}
