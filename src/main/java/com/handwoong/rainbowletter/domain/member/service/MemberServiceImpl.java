package com.handwoong.rainbowletter.domain.member.service;

import com.handwoong.rainbowletter.config.security.GrantType;
import com.handwoong.rainbowletter.config.security.JwtTokenProvider;
import com.handwoong.rainbowletter.config.security.TokenResponse;
import com.handwoong.rainbowletter.domain.mail.dto.EmailDto;
import com.handwoong.rainbowletter.domain.mail.service.event.SendEmail;
import com.handwoong.rainbowletter.domain.mail.service.template.EmailTemplateType;
import com.handwoong.rainbowletter.domain.member.dto.ChangePasswordRequest;
import com.handwoong.rainbowletter.domain.member.dto.ChangePhoneNumberRequest;
import com.handwoong.rainbowletter.domain.member.dto.FindPasswordDto;
import com.handwoong.rainbowletter.domain.member.dto.MemberLoginRequest;
import com.handwoong.rainbowletter.domain.member.dto.MemberRegisterRequest;
import com.handwoong.rainbowletter.domain.member.dto.MemberRegisterResponse;
import com.handwoong.rainbowletter.domain.member.dto.MemberResponse;
import com.handwoong.rainbowletter.domain.member.model.Member;
import com.handwoong.rainbowletter.domain.member.model.MemberStatus;
import com.handwoong.rainbowletter.domain.member.repository.MemberRepository;
import com.handwoong.rainbowletter.exception.ErrorCode;
import com.handwoong.rainbowletter.exception.RainbowLetterException;
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
