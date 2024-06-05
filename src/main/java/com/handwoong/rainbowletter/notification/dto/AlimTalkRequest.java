package com.handwoong.rainbowletter.notification.dto;

import com.handwoong.rainbowletter.member.domain.PhoneNumber;

import lombok.Builder;

@Builder
public record AlimTalkRequest(
        PhoneNumber receiver,
        String templateCode,
        String subject,
        String content,
        String failSubject,
        String failContent,
        AlimTalkButtonRequest buttons,
        boolean useEmTitle
) {
}
