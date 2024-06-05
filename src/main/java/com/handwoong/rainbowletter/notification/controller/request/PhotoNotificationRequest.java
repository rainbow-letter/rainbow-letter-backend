package com.handwoong.rainbowletter.notification.controller.request;

public record PhotoNotificationRequest(
        String receiver,
        String petName,
        String date
) {
}
