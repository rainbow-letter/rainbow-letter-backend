package com.handwoong.rainbowletter.sms.service.port;

import com.handwoong.rainbowletter.sms.domain.dto.AligoResponse;
import com.handwoong.rainbowletter.sms.domain.dto.SmsSend;
import java.io.IOException;

public interface SmsSender {
    AligoResponse send(SmsSend request) throws IOException;
}
