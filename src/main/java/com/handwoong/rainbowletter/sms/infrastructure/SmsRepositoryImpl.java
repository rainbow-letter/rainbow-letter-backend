package com.handwoong.rainbowletter.sms.infrastructure;

import com.handwoong.rainbowletter.sms.domain.Sms;
import com.handwoong.rainbowletter.sms.service.port.SmsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SmsRepositoryImpl implements SmsRepository {
    private final SmsJpaRepository smsJpaRepository;

    @Override
    public Sms save(final Sms sms) {
        return smsJpaRepository.save(SmsEntity.from(sms)).toModel();
    }
}
