package com.handwoong.rainbowletter.member.service.port;

import com.handwoong.rainbowletter.common.util.jwt.TokenResponse;
import com.handwoong.rainbowletter.member.domain.Email;
import com.handwoong.rainbowletter.member.domain.Password;

public interface AuthenticationService {
    TokenResponse login(Email email, Password password);
}
