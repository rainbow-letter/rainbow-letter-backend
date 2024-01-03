package com.handwoong.rainbowletter.member.exception;

import com.handwoong.rainbowletter.common.exception.BaseException;
import com.handwoong.rainbowletter.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class OAuthProviderTypeNotValidException extends BaseException {
    private final String registrationId;

    public OAuthProviderTypeNotValidException(final String registrationId) {
        super(ErrorCode.INVALID_OAUTH_PROVIDER_TYPE);
        this.registrationId = registrationId;
    }
}
