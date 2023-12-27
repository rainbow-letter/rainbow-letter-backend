package com.handwoong.rainbowletter.member.service;

import com.handwoong.rainbowletter.common.config.security.jwt.TokenResponse;
import com.handwoong.rainbowletter.mail.dto.EmailDto;
import com.handwoong.rainbowletter.member.controller.response.MemberRegisterResponse;
import com.handwoong.rainbowletter.member.controller.response.MemberResponse;
import com.handwoong.rainbowletter.member.domain.dto.ChangePasswordRequest;
import com.handwoong.rainbowletter.member.domain.dto.ChangePhoneNumberRequest;
import com.handwoong.rainbowletter.member.domain.dto.FindPasswordDto;
import com.handwoong.rainbowletter.member.domain.dto.MemberLoginRequest;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegisterRequest;

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
