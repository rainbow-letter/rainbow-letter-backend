package com.handwoong.rainbowletter.mock.mail;

import com.handwoong.rainbowletter.mail.domain.Mail;
import com.handwoong.rainbowletter.mail.service.port.MailRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FakeMailRepository implements MailRepository {
    private final Map<Long, Mail> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public Mail save(final Mail mail) {
        final Long id = Objects.nonNull(mail.id()) ? mail.id() : sequence++;
        final Mail saveMail = createMail(id, mail);
        database.put(id, saveMail);
        return saveMail;
    }

    private Mail createMail(final Long id, final Mail mail) {
        return Mail.builder()
                .id(id)
                .email(mail.email())
                .title(mail.title())
                .content(mail.content())
                .templateType(mail.templateType())
                .build();
    }
}
