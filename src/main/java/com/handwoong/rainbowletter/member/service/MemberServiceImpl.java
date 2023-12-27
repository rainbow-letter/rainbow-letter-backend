package com.handwoong.rainbowletter.member.service;

import com.handwoong.rainbowletter.common.config.security.jwt.GrantType;
import com.handwoong.rainbowletter.common.config.security.jwt.JwtTokenProvider;
import com.handwoong.rainbowletter.common.config.security.jwt.TokenResponse;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.common.exception.RainbowLetterException;
import com.handwoong.rainbowletter.mail.dto.EmailDto;
import com.handwoong.rainbowletter.mail.service.event.SendEmail;
import com.handwoong.rainbowletter.mail.service.template.EmailTemplateType;
import com.handwoong.rainbowletter.member.controller.response.MemberRegisterResponse;
import com.handwoong.rainbowletter.member.controller.response.MemberResponse;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import com.handwoong.rainbowletter.member.domain.dto.ChangePasswordRequest;
import com.handwoong.rainbowletter.member.domain.dto.ChangePhoneNumberRequest;
import com.handwoong.rainbowletter.member.domain.dto.FindPasswordDto;
import com.handwoong.rainbowletter.member.domain.dto.MemberLoginRequest;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegisterRequest;
import com.handwoong.rainbowletter.member.infrastructure.Member;
import com.handwoong.rainbowletter.member.infrastructure.MemberRepository;
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
    public MemberResponse info(final String email) {
        return memberRepository.findInfoByEmail(email);
    }

    @Override
    @Transactional
    public MemberRegisterResponse register(final MemberRegisterRequest request) {
        validateDuplicateEmail(request.email());

        final Member member = Member.create(request);
        member.encodePassword(passwordEncoder);
        member.changeStatus(MemberStatus.ACTIVE);
        memberRepository.save(member);
        return MemberRegisterResponse.from(member);
    }

    private void validateDuplicateEmail(final String email) {
        final boolean isExistsByEmail = memberRepository.existsByEmail(email);
        if (isExistsByEmail) {
            throw new RainbowLetterException(ErrorCode.EXISTS_EMAIL, email);
        }
    }

    @Override
    public TokenResponse login(final MemberLoginRequest request) {
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());
        final Authentication authentication = authenticationBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.generateToken(GrantType.BEARER, authentication);
    }

    @Override
    @SendEmail(type = EmailTemplateType.FIND_PASSWORD)
    public EmailDto findPassword(final FindPasswordDto request) {
        final boolean existsByEmail = memberRepository.existsByEmail(request.email());
        if (!existsByEmail) {
            throw new RainbowLetterException(ErrorCode.INVALID_EMAIL, request.email());
        }
        return request;
    }

    @Override
    @Transactional
    public void changePassword(final String email, final ChangePasswordRequest request) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_EMAIL, email));
        member.changePassword(request, passwordEncoder);
    }

    @Override
    @Transactional
    public void changePhoneNumber(final String email, final ChangePhoneNumberRequest request) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_EMAIL, email));
        member.changePhoneNumber(request);
    }

    @Override
    @Transactional
    public void deletePhoneNumber(final String email) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_EMAIL, email));
        member.deletePhoneNumber();
    }

    @Override
    @Transactional
    public void delete(final String email) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_EMAIL, email));
        member.changeStatus(MemberStatus.LEAVE);
    }
}
