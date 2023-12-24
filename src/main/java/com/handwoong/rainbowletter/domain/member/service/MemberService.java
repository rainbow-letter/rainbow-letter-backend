package com.handwoong.rainbowletter.domain.member.service;

import com.handwoong.rainbowletter.config.security.jwt.TokenResponse;
import com.handwoong.rainbowletter.domain.mail.dto.EmailDto;
import com.handwoong.rainbowletter.domain.member.dto.ChangePasswordRequest;
import com.handwoong.rainbowletter.domain.member.dto.ChangePhoneNumberRequest;
import com.handwoong.rainbowletter.domain.member.dto.FindPasswordDto;
import com.handwoong.rainbowletter.domain.member.dto.MemberLoginRequest;
import com.handwoong.rainbowletter.domain.member.dto.MemberRegisterRequest;
import com.handwoong.rainbowletter.domain.member.dto.MemberRegisterResponse;
import com.handwoong.rainbowletter.domain.member.dto.MemberResponse;

public interface MemberService {
    MemberResponse info(final String email);

    MemberRegisterResponse register(final MemberRegisterRequest request);

    TokenResponse login(final MemberLoginRequest request);

    EmailDto findPassword(final FindPasswordDto request);

    void changePassword(final String email, final ChangePasswordRequest request);

    void changePhoneNumber(final String email, final ChangePhoneNumberRequest request);

    void deletePhoneNumber(final String email);

    void delete(String email);
}
