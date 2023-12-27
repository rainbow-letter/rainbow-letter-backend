package com.handwoong.rainbowletter.common.config.security.oauth;

import com.handwoong.rainbowletter.common.config.security.password.OAuthUserPasswordGenerator;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import com.handwoong.rainbowletter.common.exception.RainbowLetterException;
import com.handwoong.rainbowletter.member.infrastructure.Member;
import com.handwoong.rainbowletter.member.domain.MemberStatus;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegisterRequest;
import com.handwoong.rainbowletter.member.infrastructure.MemberRepository;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OAuthLoginManager {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final Map<OAuthProvider, OAuthLoginUserParser> parsers = new EnumMap<>(OAuthProvider.class);

    public OAuthLoginManager(final MemberRepository memberRepository, final PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        parsers.put(OAuthProvider.GOOGLE, new GoogleLoginUserParser());
    }

    public Member process(final String registrationId, final OAuth2User user) {
        final OAuthProvider oAuthProvider = OAuthProvider.match(registrationId);
        final OAuthLoginUserParser parser = parsers.get(oAuthProvider);
        final MemberRegisterRequest registerRequest = parser.parseDetails(user, new OAuthUserPasswordGenerator());
        final String providerId = parser.parseProviderId(user);
        return saveOrUpdateMember(registerRequest, oAuthProvider, providerId);
    }

    private Member saveOrUpdateMember(final MemberRegisterRequest registerRequest,
                                      final OAuthProvider oAuthProvider,
                                      final String providerId) {
        final boolean existsByEmail = memberRepository.existsByEmail(registerRequest.email());
        if (existsByEmail) {
            return updateMember(registerRequest.email(), oAuthProvider, providerId);
        }
        return saveMember(registerRequest, oAuthProvider, providerId);
    }

    private Member saveMember(final MemberRegisterRequest registerRequest,
                              final OAuthProvider oAuthProvider,
                              final String providerId) {
        final Member member = Member.create(registerRequest, oAuthProvider, providerId);
        member.encodePassword(passwordEncoder);
        member.changeStatus(MemberStatus.ACTIVE);
        memberRepository.save(member);
        return member;
    }

    private Member updateMember(final String email, final OAuthProvider oAuthProvider, final String providerId) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.INVALID_EMAIL, email));
        member.updateProvider(oAuthProvider, providerId);
        return member;
    }
}
