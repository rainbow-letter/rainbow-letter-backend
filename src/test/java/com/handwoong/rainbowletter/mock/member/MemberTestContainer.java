package com.handwoong.rainbowletter.mock.member;

import com.handwoong.rainbowletter.member.controller.port.MemberService;
import com.handwoong.rainbowletter.member.service.MemberServiceImpl;
import com.handwoong.rainbowletter.member.service.port.AuthenticationService;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
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
        this.service = new MemberServiceImpl(passwordEncoder, repository, authenticationService);
    }
}
