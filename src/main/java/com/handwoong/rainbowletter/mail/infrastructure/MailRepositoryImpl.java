package com.handwoong.rainbowletter.mail.infrastructure;

import com.handwoong.rainbowletter.mail.domain.Mail;
import com.handwoong.rainbowletter.mail.service.port.MailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MailRepositoryImpl implements MailRepository {
    private final MailJpaRepository mailJpaRepository;

    @Override
    public Mail save(final Mail mail) {
        return mailJpaRepository.save(MailEntity.fromModel(mail)).toModel();
    }
}
