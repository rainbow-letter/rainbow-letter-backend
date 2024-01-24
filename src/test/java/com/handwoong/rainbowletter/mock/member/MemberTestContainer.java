package com.handwoong.rainbowletter.mock.member;

import com.handwoong.rainbowletter.mail.service.MailServiceImpl;
import com.handwoong.rainbowletter.member.controller.port.MemberService;
import com.handwoong.rainbowletter.member.service.MemberServiceImpl;
import com.handwoong.rainbowletter.member.service.port.AuthenticationService;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import com.handwoong.rainbowletter.mock.mail.FakeMailRepository;
import com.handwoong.rainbowletter.mock.mail.FakeMailSender;
import com.handwoong.rainbowletter.mock.mail.FakeMailTemplateManager;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberTestContainer {
    public final PasswordEncoder passwordEncoder;
    public final MemberRepository repository;
    public final MemberService service;
    public final AuthenticationService authenticationService;

    public MemberTestContainer() {
        this.passwordEncoder = new FakePasswordEncoder("$$$!!@@@");
        this.repository = new FakeMemberRepository();
        this.authenticationService = new FakeAuthenticationService(repository);

        final MailServiceImpl mailService = new MailServiceImpl(
                new FakeMailSender(), new FakeMailTemplateManager("제목", "본문"), new FakeMailRepository());
        this.service = new MemberServiceImpl(mailService, passwordEncoder, repository, authenticationService);
    }
}
