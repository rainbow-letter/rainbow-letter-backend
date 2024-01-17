package com.handwoong.rainbowletter.sms.service;

import com.handwoong.rainbowletter.sms.domain.Sms;
import com.handwoong.rainbowletter.sms.domain.dto.SmsCreate;
import com.handwoong.rainbowletter.sms.domain.dto.SmsSend;
import com.handwoong.rainbowletter.sms.service.port.SmsRepository;
import com.handwoong.rainbowletter.sms.service.port.SmsSender;
import com.handwoong.rainbowletter.sms.service.port.SmsService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SmsServiceImpl implements SmsService {
    private final SmsSender smsSender;
    private final SmsRepository smsRepository;

    @Override
    @Async
    @Transactional
    public void send(final SmsSend request) {
        try {
            final SmsCreate smsCreate = smsSender.send(request);
            final Sms sms = Sms.create(smsCreate);
            smsRepository.save(sms);
            if (sms.code() < 0) {
                throw new IllegalStateException();
            }
        } catch (final IOException | IllegalStateException exception) {
            log.error("문자 발송에 실패하였습니다. [" + request.receiver().phoneNumber() + "] " + request.content());
        }
    }
}
