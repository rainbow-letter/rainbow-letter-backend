package com.handwoong.rainbowletter.mock.sms;

import com.handwoong.rainbowletter.sms.domain.Sms;
import com.handwoong.rainbowletter.sms.service.port.SmsRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FakeSmsRepository implements SmsRepository {
    private final Map<Long, Sms> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public Sms save(final Sms sms) {
        final Long id = Objects.nonNull(sms.id()) ? sms.id() : sequence++;
        final Sms saveSms = createSms(id, sms);
        database.put(id, saveSms);
        return saveSms;
    }

    private Sms createSms(final Long id, final Sms sms) {
        return Sms.builder()
                .id(id)
                .code(sms.code())
                .statusMessage(sms.statusMessage())
                .receiver(sms.receiver())
                .sender(sms.sender())
                .content(sms.content())
                .build();
    }
}
