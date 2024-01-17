package com.handwoong.rainbowletter.sms.domain.dto;

import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import lombok.Builder;

@Builder
public record SmsSend(PhoneNumber receiver, String content) {
}
