package com.handwoong.rainbowletter.sms.service.port;

import com.handwoong.rainbowletter.sms.domain.Sms;

public interface SmsRepository {
    Sms save(Sms sms);
}
