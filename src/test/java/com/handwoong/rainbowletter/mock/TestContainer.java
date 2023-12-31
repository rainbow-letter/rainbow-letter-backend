package com.handwoong.rainbowletter.mock;

import com.handwoong.rainbowletter.faq.controller.port.FAQService;
import com.handwoong.rainbowletter.faq.service.FAQServiceImpl;
import com.handwoong.rainbowletter.faq.service.port.FAQRepository;
import com.handwoong.rainbowletter.member.controller.port.MemberService;
import com.handwoong.rainbowletter.member.service.MemberServiceImpl;
import com.handwoong.rainbowletter.member.service.port.AuthenticationService;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestContainer {
    public final PasswordEncoder passwordEncoder;
    public final MemberRepository memberRepository;
    public final MemberService memberService;
    public final AuthenticationService authenticationService;

    public final FAQRepository faqRepository;
    public final FAQService faqService;

    public TestContainer() {
        this.passwordEncoder = new FakePasswordEncoder("$$$!!@@@");
        this.memberRepository = new FakeMemberRepository();
        this.authenticationService = new FakeAuthenticationService(memberRepository);
        this.memberService = new MemberServiceImpl(passwordEncoder, memberRepository, authenticationService);

        this.faqRepository = new FakeFaqRepository();
        this.faqService = new FAQServiceImpl(faqRepository);
    }
}
