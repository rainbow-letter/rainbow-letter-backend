package com.handwoong.rainbowletter.member.controller.port;

import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.mail.domain.dto.MailDto;
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

    Member info(Email email);

    Member register(MemberRegister request);

    boolean existsByEmail(Email email);

    TokenResponse login(MemberLogin request);

    MailDto findPassword(FindPassword request);

    Member changePassword(Email email, ChangePassword request);

    Member resetPassword(Email email, ResetPassword request);

    Member updatePhoneNumber(Email email, PhoneNumberUpdate request);

    Member deletePhoneNumber(Email email);

    Member delete(Email email);
}
