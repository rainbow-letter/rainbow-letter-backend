package com.handwoong.rainbowletter.mock.sms;

import com.handwoong.rainbowletter.sms.domain.dto.SmsCreate;
import com.handwoong.rainbowletter.sms.domain.dto.SmsSend;
import com.handwoong.rainbowletter.sms.service.port.SmsSender;
import java.io.IOException;

public class FakeSmsSender implements SmsSender {
    @Override
    public SmsCreate send(final SmsSend request) throws IOException {
        return SmsCreate.builder()
                .code(1)
                .statusMessage("success")
                .content("콘텐츠")
                .receiver(request.receiver().phoneNumber())
                .content(request.content())
                .build();
    }
}
