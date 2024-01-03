package com.handwoong.rainbowletter.mock.member;

import com.handwoong.rainbowletter.common.util.jwt.GrantType;
import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.Password;
import com.handwoong.rainbowletter.member.service.port.AuthenticationService;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import org.springframework.security.authentication.BadCredentialsException;

public class FakeAuthenticationService implements AuthenticationService {
    private final MemberRepository memberRepository;

    public FakeAuthenticationService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public TokenResponse login(final Email email, final Password password) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("자격 증명에 실패하였습니다."));
        final boolean compareEmail = member.email().toString().equals(email.toString());
        final boolean comparePassword = member.password().toString().equals(password.toString());
        if (!compareEmail || !comparePassword) {
            throw new BadCredentialsException("자격 증명에 실패하였습니다.");
        }
        return TokenResponse.of(GrantType.BEARER, "accessToken");
    }
}
