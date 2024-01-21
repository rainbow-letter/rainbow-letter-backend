package com.handwoong.rainbowletter.member.service;

import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.mail.domain.MailTemplateType;
import com.handwoong.rainbowletter.mail.domain.dto.MailDto;
import com.handwoong.rainbowletter.mail.service.port.MailService;
import com.handwoong.rainbowletter.member.controller.port.MemberService;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import com.handwoong.rainbowletter.member.domain.dto.ChangePassword;
import com.handwoong.rainbowletter.member.domain.dto.FindPassword;
import com.handwoong.rainbowletter.member.domain.dto.MemberLogin;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import com.handwoong.rainbowletter.member.domain.dto.PhoneNumberUpdate;
import com.handwoong.rainbowletter.member.domain.dto.ResetPassword;
import com.handwoong.rainbowletter.member.exception.DuplicateEmailException;
import com.handwoong.rainbowletter.member.exception.MemberEmailNotFoundException;
import com.handwoong.rainbowletter.member.service.port.AuthenticationService;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AuthenticationService authenticationService;

    @Override
    public Member info(final Email email) {
        return memberRepository.findByEmailOrElseThrow(email);
    }

    @Override
    @Transactional
    public Member register(final MemberRegister request) {
        validateDuplicateEmail(request.email());
        final Member member = Member.create(request, passwordEncoder);
        return memberRepository.save(member);
    }

    private void validateDuplicateEmail(final Email email) {
        if (existsByEmail(email)) {
            throw new DuplicateEmailException(email.toString());
        }
    }

    @Override
    public boolean existsByEmail(final Email email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public TokenResponse login(final MemberLogin request) {
        return authenticationService.login(request.email(), request.password());
    }

    @Override
    public void findPassword(final FindPassword request) {
        if (!existsByEmail(request.email())) {
            throw new MemberEmailNotFoundException(request.email().toString());
        }
        sendNotificationMail(request.email());
    }

    @Override
    @Transactional
    public Member changePassword(final Email email, final ChangePassword request) {
        final Member member = memberRepository.findByEmailOrElseThrow(email);
        final Member updateMember = member.changePassword(request, passwordEncoder);
        return memberRepository.save(updateMember);
    }

    @Override
    @Transactional
    public Member resetPassword(final Email email, final ResetPassword request) {
        final Member member = memberRepository.findByEmailOrElseThrow(email);
        final Member updateMember = member.resetPassword(request, passwordEncoder);
        return memberRepository.save(updateMember);
    }

    @Override
    @Transactional
    public Member updatePhoneNumber(final Email email, final PhoneNumberUpdate request) {
        final Member member = memberRepository.findByEmailOrElseThrow(email);
        final Member updateMember = member.update(request);
        return memberRepository.save(updateMember);
    }

    @Override
    @Transactional
    public Member deletePhoneNumber(final Email email) {
        final Member member = memberRepository.findByEmailOrElseThrow(email);
        final Member updateMember = member.deletePhoneNumber();
        return memberRepository.save(updateMember);
    }

    @Override
    @Transactional
    public Member delete(final Email email) {
        final Member member = memberRepository.findByEmailOrElseThrow(email);
        final Member updateMember = member.update(MemberStatus.LEAVE);
        return memberRepository.save(updateMember);
    }

    private void sendNotificationMail(final Email email) {
        final MailDto mailDto = MailDto.builder()
                .email(email)
                .subject("비밀번호를 다시 설정해주세요!")
                .url("/members/password/reset")
                .build();
        mailService.send(MailTemplateType.FIND_PASSWORD, mailDto);
    }
}
