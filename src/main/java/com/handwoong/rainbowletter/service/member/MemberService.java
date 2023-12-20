package com.handwoong.rainbowletter.service.member;

import com.handwoong.rainbowletter.config.security.TokenResponse;
import com.handwoong.rainbowletter.dto.mail.EmailDto;
import com.handwoong.rainbowletter.dto.member.ChangePasswordRequest;
import com.handwoong.rainbowletter.dto.member.ChangePhoneNumberRequest;
import com.handwoong.rainbowletter.dto.member.FindPasswordDto;
import com.handwoong.rainbowletter.dto.member.MemberLoginRequest;
import com.handwoong.rainbowletter.dto.member.MemberRegisterRequest;
import com.handwoong.rainbowletter.dto.member.MemberRegisterResponse;
import com.handwoong.rainbowletter.dto.member.MemberResponse;

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
