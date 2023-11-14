package com.handwoong.rainbowletter.service.member;

import com.handwoong.rainbowletter.dto.member.MemberRegisterRequest;
import com.handwoong.rainbowletter.dto.member.MemberRegisterResponse;

public interface MemberService {
    MemberRegisterResponse register(final MemberRegisterRequest request);
}
