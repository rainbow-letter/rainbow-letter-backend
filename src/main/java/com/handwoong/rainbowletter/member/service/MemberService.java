package com.handwoong.rainbowletter.member.service;

import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.mail.dto.EmailDto;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Member;
import com.handwoong.rainbowletter.member.domain.dto.ChangePassword;
import com.handwoong.rainbowletter.member.domain.dto.FindPassword;
import com.handwoong.rainbowletter.member.domain.dto.MemberLogin;
import com.handwoong.rainbowletter.member.domain.dto.MemberRegister;
import com.handwoong.rainbowletter.member.domain.dto.PhoneNumberUpdate;
import com.handwoong.rainbowletter.member.domain.dto.ResetPassword;

public interface MemberService {
    Member info(String email);

    Member register(MemberRegister request);

    boolean existsByEmail(Email email);

    TokenResponse login(MemberLogin request);

    EmailDto findPassword(FindPassword request);

    void changePassword(String email, ChangePassword request);

    void resetPassword(String email, ResetPassword request);

    void updatePhoneNumber(String email, PhoneNumberUpdate request);

    void deletePhoneNumber(String email);

    void delete(String email);
}
