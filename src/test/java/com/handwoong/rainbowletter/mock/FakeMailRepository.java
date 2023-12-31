package com.handwoong.rainbowletter.mock;

import com.handwoong.rainbowletter.mail.domain.Mail;
import com.handwoong.rainbowletter.mail.service.port.MailRepository;
import java.util.HashMap;
import java.util.Map;

public class FakeMailRepository implements MailRepository {
    private final Map<Long, Mail> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public Mail save(final Mail mail) {
        if (mail.id() != null) {
            final Mail updateMail = Mail.builder()
                    .id(mail.id())
                    .email(mail.email())
                    .title(mail.title())
                    .content(mail.content())
                    .templateType(mail.templateType())
                    .build();
            database.put(mail.id(), updateMail);
            return updateMail;
        }
        final Mail saveMail = Mail.builder()
                .id(sequence)
                .email(mail.email())
                .title(mail.title())
                .content(mail.content())
                .templateType(mail.templateType())
                .build();
        database.put(sequence, saveMail);
        sequence++;
        return saveMail;
    }
}
