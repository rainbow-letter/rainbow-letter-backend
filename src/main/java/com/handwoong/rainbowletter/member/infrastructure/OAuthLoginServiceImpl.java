package com.handwoong.rainbowletter.member.infrastructure;

import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import com.handwoong.rainbowletter.member.exception.MemberEmailNotFoundException;
import com.handwoong.rainbowletter.member.infrastructure.password.OAuthUserPasswordGenerator;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import com.handwoong.rainbowletter.member.service.port.OAuthLoginService;
import com.handwoong.rainbowletter.member.service.port.OAuthLoginUserParser;
import com.handwoong.rainbowletter.member.service.port.OAuthProvider;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OAuthLoginServiceImpl implements OAuthLoginService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final Map<OAuthProvider, OAuthLoginUserParser> parsers = new EnumMap<>(OAuthProvider.class);

    public OAuthLoginServiceImpl(final MemberRepository memberRepository, final PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        parsers.put(OAuthProvider.GOOGLE, new GoogleLoginUserParser());
    }

    @Override
    public Member process(final String registrationId, final OAuth2User user) {
        final OAuthProvider provider = OAuthProvider.match(registrationId);
        final OAuthLoginUserParser parser = parsers.get(provider);
        final MemberRegister request = parser.parseDetails(user, new OAuthUserPasswordGenerator());
        final String providerId = parser.parseProviderId(user);
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
        memberRepository.save(member);
        return member;
    }

    private Member update(final Email email, final OAuthProvider provider, final String providerId) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberEmailNotFoundException(email.toString()));
        final Member updateMember = member.update(provider, providerId);
        memberRepository.save(updateMember);
        return updateMember;
    }
}
