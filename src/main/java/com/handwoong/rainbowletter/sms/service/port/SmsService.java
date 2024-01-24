package com.handwoong.rainbowletter.sms.service.port;

import com.handwoong.rainbowletter.sms.domain.dto.SmsSend;

public interface SmsService {
    void send(SmsSend request);
}
