package com.handwoong.rainbowletter.member.infrastructure.oauth;

import com.handwoong.rainbowletter.common.service.port.UuidGenerator;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.OAuthProvider;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import com.handwoong.rainbowletter.member.exception.MemberEmailNotFoundException;
import com.handwoong.rainbowletter.member.service.port.MemberRepository;
import com.handwoong.rainbowletter.member.service.port.OAuthLoginService;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OAuthLoginServiceImpl implements OAuthLoginService {
    private final UuidGenerator uuidGenerator;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final Map<OAuthProvider, OAuthLoginUserParser> parsers = new EnumMap<>(OAuthProvider.class);

    public OAuthLoginServiceImpl(final UuidGenerator uuidGenerator,
                                 final PasswordEncoder passwordEncoder,
                                 final MemberRepository memberRepository) {
        this.uuidGenerator = uuidGenerator;
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        parsers.put(OAuthProvider.GOOGLE, new GoogleLoginUserParser());
    }

    @Override
    public Member process(final String registrationId, final OAuth2User user) {
        final OAuthProvider provider = OAuthProvider.match(registrationId);
        final OAuthLoginUserParser parser = parsers.get(provider);
        final MemberRegister request = parser.parseDetails(user, uuidGenerator);
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
        return memberRepository.save(member);
    }

    private Member update(final Email email, final OAuthProvider provider, final String providerId) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberEmailNotFoundException(email.toString()));
        final Member updateMember = member.update(provider, providerId);
        return memberRepository.save(updateMember);
    }
}
