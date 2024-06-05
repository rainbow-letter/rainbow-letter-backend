package com.handwoong.rainbowletter.notification.application.port;

import java.io.IOException;

import com.handwoong.rainbowletter.notification.dto.AlimTalkRequest;
import com.handwoong.rainbowletter.sms.domain.dto.AligoResponse;

public interface MessageSender {
    AligoResponse send(AlimTalkRequest request) throws IOException;
}
