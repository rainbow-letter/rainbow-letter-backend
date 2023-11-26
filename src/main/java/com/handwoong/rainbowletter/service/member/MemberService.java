package com.handwoong.rainbowletter.service.member;

import com.handwoong.rainbowletter.config.security.TokenResponse;
import com.handwoong.rainbowletter.dto.member.MemberLoginRequest;
import com.handwoong.rainbowletter.dto.member.MemberRegisterRequest;
import com.handwoong.rainbowletter.dto.member.MemberRegisterResponse;

public interface MemberService {
    MemberRegisterResponse register(final MemberRegisterRequest request);

    void verify(final String token);

    TokenResponse login(final MemberLoginRequest request);
}
