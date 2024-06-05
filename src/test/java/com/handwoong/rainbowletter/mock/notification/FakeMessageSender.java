package com.handwoong.rainbowletter.mock.notification;

import java.io.IOException;

import com.handwoong.rainbowletter.notification.application.port.MessageSender;
import com.handwoong.rainbowletter.notification.dto.AlimTalkRequest;
import com.handwoong.rainbowletter.sms.domain.dto.AligoResponse;

public class FakeMessageSender implements MessageSender {
    @Override
    public AligoResponse send(final AlimTalkRequest request) throws IOException {
        return new AligoResponse(0, "성공");
    }
}
