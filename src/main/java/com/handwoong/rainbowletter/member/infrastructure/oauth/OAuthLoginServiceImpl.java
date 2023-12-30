package com.handwoong.rainbowletter.member.infrastructure.oauth;

import com.handwoong.rainbowletter.common.service.port.UuidGenerator;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.OAuthProvider;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import com.handwoong.rainbowletter.member.exception.MemberEmailNotFoundException;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import com.handwoong.rainbowletter.member.service.port.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class OAuthLoginServiceImpl implements OAuthLoginService {
    private final UuidGenerator uuidGenerator;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final OAuthLoginUserParsingManager parsingManager;

    @Override
    public Member process(final String registrationId, final OAuth2User oauth2User) {
        final OAuthProvider provider = OAuthProvider.match(registrationId);
        final MemberRegister request = parsingManager.getRegisterRequest(provider, oauth2User, uuidGenerator);
        final String providerId = parsingManager.getProviderId(provider, oauth2User);
        return saveOrUpdate(request, provider, providerId);
    }

    private Member saveOrUpdate(final MemberRegister request, final OAuthProvider provider, final String providerId) {
        final boolean existsByEmail = memberRepository.existsByEmail(request.email());
        if (existsByEmail) {
            return update(request.email(), provider, providerId);
        }
        return save(request, provider, providerId);
    }

    private Member save(final MemberRegister request, final OAuthProvider provider, final String providerId) {
        final Member member = Member.create(request, passwordEncoder, provider, providerId);
        return memberRepository.save(member);
    }

    private Member update(final Email email, final OAuthProvider provider, final String providerId) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberEmailNotFoundException(email.toString()));
        final Member updateMember = member.update(provider, providerId);
        return memberRepository.save(updateMember);
    }
}
