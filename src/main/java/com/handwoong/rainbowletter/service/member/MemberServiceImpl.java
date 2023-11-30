package com.handwoong.rainbowletter.service.member;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.handwoong.rainbowletter.config.security.GrantType;
import com.handwoong.rainbowletter.config.security.JwtTokenProvider;
import com.handwoong.rainbowletter.config.security.TokenResponse;
import com.handwoong.rainbowletter.domain.member.Member;
import com.handwoong.rainbowletter.domain.member.MemberStatus;
import com.handwoong.rainbowletter.dto.member.MemberLoginRequest;
import com.handwoong.rainbowletter.dto.member.MemberRegisterRequest;
import com.handwoong.rainbowletter.dto.member.MemberRegisterResponse;
import com.handwoong.rainbowletter.exception.ErrorCode;
import com.handwoong.rainbowletter.exception.RainbowLetterException;
import com.handwoong.rainbowletter.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationBuilder;

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

    @Override
    public void verify(final String token) {
        final String email = tokenProvider.parseVerifyToken(token);
        final boolean isExistsByEmail = memberRepository.existsByEmail(email);
        if (!isExistsByEmail) {
            throw new RainbowLetterException(ErrorCode.INVALID_EMAIL, email);
        }
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
}
