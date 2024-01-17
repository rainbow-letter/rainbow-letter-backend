package com.handwoong.rainbowletter.sms.domain;

import com.handwoong.rainbowletter.member.domain.PhoneNumber;
import com.handwoong.rainbowletter.sms.domain.dto.SmsCreate;
import lombok.Builder;

@Builder
public record Sms(Long id, int code, String statusMessage, PhoneNumber sender, PhoneNumber receiver, String content) {
    public static Sms create(final SmsCreate smsCreate) {
        return Sms.builder()
                .code(smsCreate.code())
                .statusMessage(smsCreate.statusMessage())
                .sender(new PhoneNumber(smsCreate.sender()))
                .receiver(new PhoneNumber(smsCreate.receiver()))
                .content(smsCreate.content())
                .build();
    }
}
