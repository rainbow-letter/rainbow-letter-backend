package com.handwoong.rainbowletter.mail.service.port;

import com.handwoong.rainbowletter.mail.domain.Mail;

public interface MailRepository {
    Mail save(Mail mail);
}
