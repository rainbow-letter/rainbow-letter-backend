package com.handwoong.rainbowletter.sms.domain.dto;

import lombok.Builder;

@Builder
public record SmsCreate(int code, String statusMessage, String sender, String receiver, String content) {
}
