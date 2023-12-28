package com.handwoong.rainbowletter.member.service;

import com.handwoong.rainbowletter.common.util.jwt.GrantType;
import com.handwoong.rainbowletter.common.util.jwt.JwtTokenProvider;
import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.mail.domain.EmailTemplateType;
import com.handwoong.rainbowletter.mail.domain.SendEmail;
import com.handwoong.rainbowletter.mail.domain.dto.EmailDto;
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
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationBuilder;

    @Override
    public Member info(final String email) {
        return memberRepository.findInfoByEmail(new Email(email))
                .orElseThrow(() -> new MemberEmailNotFoundException(email));
    }

    @Override
    @Transactional
    public Member register(final MemberRegister request) {
        validateDuplicateEmail(request.email());
        final Member member = Member.create(request, passwordEncoder);
        memberRepository.save(member);
        return member;
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
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.email().toString(), request.password().toString());
        final Authentication authentication = authenticationBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.generateToken(GrantType.BEARER, authentication);
    }

    @Override
    @SendEmail(type = EmailTemplateType.FIND_PASSWORD)
    public EmailDto findPassword(final FindPassword request) {
        if (!existsByEmail(request.email())) {
            throw new MemberEmailNotFoundException(request.email().toString());
        }
        return request;
    }

    @Override
    @Transactional
    public void changePassword(final String email, final ChangePassword request) {
        final Member member = findByEmailOrElseThrow(new Email(email));
        final Member updateMember = member.changePassword(request, passwordEncoder);
        memberRepository.save(updateMember);
    }

    @Override
    @Transactional
    public void resetPassword(final String email, final ResetPassword request) {
        final Member member = findByEmailOrElseThrow(new Email(email));
        final Member updateMember = member.resetPassword(request, passwordEncoder);
        memberRepository.save(updateMember);
    }

    @Override
    @Transactional
    public void updatePhoneNumber(final String email, final PhoneNumberUpdate request) {
        final Member member = findByEmailOrElseThrow(new Email(email));
        final Member updateMember = member.update(request);
        memberRepository.save(updateMember);
    }

    @Override
    @Transactional
    public void deletePhoneNumber(final String email) {
        final Member member = findByEmailOrElseThrow(new Email(email));
        final Member updateMember = member.deletePhoneNumber();
        memberRepository.save(updateMember);
    }

    @Override
    @Transactional
    public void delete(final String email) {
        final Member member = findByEmailOrElseThrow(new Email(email));
        final Member updateMember = member.update(MemberStatus.LEAVE);
        memberRepository.save(updateMember);
    }

    private Member findByEmailOrElseThrow(final Email email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberEmailNotFoundException(email.toString()));
    }
}
