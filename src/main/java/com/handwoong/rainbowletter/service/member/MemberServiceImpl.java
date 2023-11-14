package com.handwoong.rainbowletter.service.member;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.handwoong.rainbowletter.config.security.JwtTokenProvider;
import com.handwoong.rainbowletter.domain.member.Member;
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
    private final AuthenticationManagerBuilder managerBuilder;

    @Override
    @Transactional
    public MemberRegisterResponse register(final MemberRegisterRequest request) {
        validateDuplicateEmail(request.email());

        final Member member = Member.create(request);
        member.encodePassword(passwordEncoder);
        memberRepository.save(member);
        return MemberRegisterResponse.from(member);
    }

    private void validateDuplicateEmail(final String email) {
        final boolean isExistsByUsername = memberRepository.existsByEmail(email);
        if (isExistsByUsername) {
            throw new RainbowLetterException(ErrorCode.EXISTS_EMAIL, email);
        }
    }
}
