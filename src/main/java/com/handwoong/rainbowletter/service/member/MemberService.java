package com.handwoong.rainbowletter.service.member;

import com.handwoong.rainbowletter.config.security.TokenResponse;
import com.handwoong.rainbowletter.dto.mail.EmailDto;
import com.handwoong.rainbowletter.dto.member.ChangePasswordRequest;
import com.handwoong.rainbowletter.dto.member.ChangePhoneNumberRequest;
import com.handwoong.rainbowletter.dto.member.FindPasswordDto;
import com.handwoong.rainbowletter.dto.member.MemberLoginRequest;
import com.handwoong.rainbowletter.dto.member.MemberRegisterRequest;
import com.handwoong.rainbowletter.dto.member.MemberRegisterResponse;

public interface MemberService {
    MemberRegisterResponse register(final MemberRegisterRequest request);

    void verify(final String token);

    TokenResponse login(final MemberLoginRequest request);

    EmailDto findPassword(final FindPasswordDto request);

    void changePassword(final String email, final ChangePasswordRequest request);

    void changePhoneNumber(final String email, final ChangePhoneNumberRequest request);
}
