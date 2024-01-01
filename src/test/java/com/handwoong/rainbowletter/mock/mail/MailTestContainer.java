package com.handwoong.rainbowletter.mock.mail;

import com.handwoong.rainbowletter.mail.service.port.MailRepository;
import com.handwoong.rainbowletter.mail.service.port.MailSender;

public class MailTestContainer {
    public final MailSender sender;
    public final MailRepository repository;

    public MailTestContainer() {
        this.sender = new FakeMailSender();
        this.repository = new FakeMailRepository();
    }
}
