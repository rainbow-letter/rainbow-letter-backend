package com.handwoong.rainbowletter.notification.application;

import java.io.IOException;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.handwoong.rainbowletter.notification.application.port.MessageSender;
import com.handwoong.rainbowletter.notification.controller.port.NotificationService;
import com.handwoong.rainbowletter.notification.dto.AlimTalkRequest;
import com.handwoong.rainbowletter.sms.domain.dto.AligoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {
    private final MessageSender messageSender;

    @Override
    @Async
    public void send(final AlimTalkRequest request) {
        try {
            final AligoResponse response = messageSender.send(request);
            System.out.println("응답 : " + response.message());
            if (response.result_code() < 0) {
                throw new IllegalStateException();
            }
        } catch (final IOException | IllegalStateException exception) {
            log.error("알림톡 발송에 실패하였습니다. [" + request.receiver().phoneNumber() + "] " + request.content());
        }
    }
}
