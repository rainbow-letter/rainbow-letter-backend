package com.handwoong.rainbowletter.member.service;

import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.mail.domain.EmailTemplateType;
import com.handwoong.rainbowletter.mail.domain.SendEmail;
import com.handwoong.rainbowletter.mail.domain.dto.EmailDto;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import com.handwoong.rainbowletter.member.domain.Password;
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
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AuthenticationService authenticationService;

    @Override
    public Member findByEmailOrElseThrow(final Email email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberEmailNotFoundException(email.toString()));
    }

    @Override
    public Member info(final String email) {
        return memberRepository.findInfoByEmail(new Email(email))
                .orElseThrow(() -> new MemberEmailNotFoundException(email));
    }

    @Override
    @Transactional
    public Member register(final MemberRegister request) {
        validateDuplicateEmail(new Email(request.email()));
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
        return authenticationService.login(new Email(request.email()), new Password(request.password()));
    }

    @Override
    @SendEmail(type = EmailTemplateType.FIND_PASSWORD)
    public EmailDto findPassword(final FindPassword request) {
        if (!existsByEmail(new Email(request.email()))) {
            throw new MemberEmailNotFoundException(request.email());
        }
        return request;
    }

    @Override
    @Transactional
    public Member changePassword(final String email, final ChangePassword request) {
        final Member member = findByEmailOrElseThrow(new Email(email));
        final Member updateMember = member.changePassword(request, passwordEncoder);
        return memberRepository.save(updateMember);
    }

    @Override
    @Transactional
    public Member resetPassword(final String email, final ResetPassword request) {
        final Member member = findByEmailOrElseThrow(new Email(email));
        final Member updateMember = member.resetPassword(request, passwordEncoder);
        return memberRepository.save(updateMember);
    }

    @Override
    @Transactional
    public Member updatePhoneNumber(final String email, final PhoneNumberUpdate request) {
        final Member member = findByEmailOrElseThrow(new Email(email));
        final Member updateMember = member.update(request);
        return memberRepository.save(updateMember);
    }

    @Override
    @Transactional
    public Member deletePhoneNumber(final String email) {
        final Member member = findByEmailOrElseThrow(new Email(email));
        final Member updateMember = member.deletePhoneNumber();
        return memberRepository.save(updateMember);
    }

    @Override
    @Transactional
    public Member delete(final String email) {
        final Member member = findByEmailOrElseThrow(new Email(email));
        final Member updateMember = member.update(MemberStatus.LEAVE);
        return memberRepository.save(updateMember);
    }
}
