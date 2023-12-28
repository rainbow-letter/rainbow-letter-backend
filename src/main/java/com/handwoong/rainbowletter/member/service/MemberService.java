package com.handwoong.rainbowletter.member.service;

import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.mail.domain.dto.EmailDto;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.dto.ChangePassword;
import com.handwoong.rainbowletter.member.domain.dto.FindPassword;
import com.handwoong.rainbowletter.member.domain.dto.MemberLogin;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import com.handwoong.rainbowletter.member.domain.dto.PhoneNumberUpdate;
import com.handwoong.rainbowletter.member.domain.dto.ResetPassword;

public interface MemberService {
    Member findByEmailOrElseThrow(Email email);

    Member info(String email);

    Member register(MemberRegister request);

    boolean existsByEmail(Email email);

    TokenResponse login(MemberLogin request);

    EmailDto findPassword(FindPassword request);

    Member changePassword(String email, ChangePassword request);

    Member resetPassword(String email, ResetPassword request);

    Member updatePhoneNumber(String email, PhoneNumberUpdate request);

    Member deletePhoneNumber(String email);

    Member delete(String email);
}
