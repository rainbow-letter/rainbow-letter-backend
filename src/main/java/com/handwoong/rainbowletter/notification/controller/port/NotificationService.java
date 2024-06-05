package com.handwoong.rainbowletter.notification.controller.port;

import com.handwoong.rainbowletter.notification.dto.AlimTalkRequest;

public interface NotificationService {
    void send(AlimTalkRequest request);
}
