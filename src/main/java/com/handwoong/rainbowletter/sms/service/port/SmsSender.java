package com.handwoong.rainbowletter.sms.service.port;

import com.handwoong.rainbowletter.sms.domain.dto.SmsCreate;
import com.handwoong.rainbowletter.sms.domain.dto.SmsSend;
import java.io.IOException;

public interface SmsSender {
    SmsCreate send(SmsSend request) throws IOException;
}
