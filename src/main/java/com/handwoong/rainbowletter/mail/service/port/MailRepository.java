package com.handwoong.rainbowletter.mail.service.port;

import com.handwoong.rainbowletter.mail.domain.Mail;

public interface MailRepository {
    void save(Mail mail);
}
