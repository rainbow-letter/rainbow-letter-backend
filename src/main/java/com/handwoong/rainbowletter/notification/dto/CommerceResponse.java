package com.handwoong.rainbowletter.notification.dto;

public record CommerceResponse(
        String access_token,
        int expires_in,
        String token_type
) {
}
